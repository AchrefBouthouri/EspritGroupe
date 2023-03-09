package com.pi.bookingservice.service;

import com.itextpdf.text.DocumentException;
import com.pi.bookingservice.dto.Room;
import com.pi.bookingservice.dto.Statistics;
import com.pi.bookingservice.model.Booking;
import com.pi.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@EnableScheduling
public class BookingService {
    @Autowired
    //private RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private QrCodeGenerator qrCodeGenerator;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${paiement-service.url}")
    private String paymentServiceUrl;
    public Booking createBooking(Booking booking, String token, Double amount, String currency) {
        boolean isRoomAvailable = checkRoomAvailability(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());
        if (!isRoomAvailable) {
            throw new RuntimeException("room introuvable");
        }
        Room room = getRoomById(booking.getRoomId());
        int numNights = (int) ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        Double totalPrice = room.getPrice() * numNights;
        booking.setTotalPrice(totalPrice);
        try {
            chargeCreditCard(token, totalPrice, currency);
        } catch (Exception e) {
            throw new RuntimeException("Payment error");
        }
        String pdfFilePath = "booking_qrcode.pdf";
        try {
            qrCodeGenerator.generatePdfQrCode(booking);
            emailService.confirmationEmail3(booking, pdfFilePath);
        } catch (DocumentException | IOException | MessagingException e) {
            e.printStackTrace();
        }
        Booking savedBooking = bookingRepository.save(booking);

        updateRoomAvailability(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());
        log.info("Booking : {}", savedBooking);
        return savedBooking;
    }

    private boolean checkRoomAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        String url = "http://availability-service/api/availability/check?roomId={roomId}&startDate={startDate}&endDate={endDate}";
        Boolean isAvailable = webClientBuilder.build()
                .get()
                .uri(url, roomId, startDate, endDate)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return isAvailable;
    }

    public Booking extendBooking(long bookingId, LocalDate newEndDate, String token,Double amount, String currency) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        Room room = getRoomById(booking.getRoomId());
        int numNights = (int) ChronoUnit.DAYS.between(booking.getEndDate(), newEndDate);
        Double totalPrice = room.getPrice() * numNights;
        boolean isRoomAvailable = checkRoomAvailability(booking.getRoomId(), booking.getEndDate(), newEndDate);
        if (!isRoomAvailable) {
            throw new RuntimeException("The room is not available");
        }
        try {
            chargeCreditCard(token, totalPrice, currency);
        } catch (Exception e) {
            throw new RuntimeException("Payment not completed");
        }
        booking.setEndDate(newEndDate);
        booking.setTotalPrice(booking.getTotalPrice() + totalPrice);
        updateRoomAvailability(booking.getRoomId(), booking.getStartDate(), newEndDate);
        emailService.confirmationEmail2(booking);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException(" not found"));
        bookingRepository.delete(booking);
        updateRoomAvailability(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());
        emailService.cancellationEmail(booking);
    }

    private void updateRoomAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        String url = "http://availability-service/api/availability/update?roomId={roomId}&startDate={startDate}&endDate={endDate}";
        webClientBuilder.build()
                .put()
                .uri(url, roomId, startDate, endDate)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private Room getRoomById(String roomId) {
        String url = "http://room-service/api/room/{roomId}";
        Room room = webClientBuilder.build()
                .get()
                .uri(url, roomId)
                .retrieve()
                .bodyToMono(Room.class)
                .block();
        if (room == null) {
            throw new RuntimeException("Room with id " + roomId + " not found");
        }
        return room;
    }

    private void chargeCreditCard(String token, Double amount, String currency) {
        String url = paymentServiceUrl + "/api/payment/charge?token={token}&amount={amount}&currency={currency}";
        try {
            webClientBuilder.build()
                    .post()
                    .uri(url, token, amount, currency)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Payment not completed");
        }
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    private List<Booking> scheduel() {
        LocalDate currentDate = LocalDate.now();
        LocalDate oneWeekFromNow = currentDate.plusDays(7);
        return bookingRepository.findByEndDateBetween(currentDate, oneWeekFromNow);
    }

    @Scheduled(fixedRate = 300000)
    //@Scheduled(cron = "0 0 0 * * *")
    public void NotificationEmail() {
        List<Booking> bookingsEndingInOneWeek = scheduel();
        for (Booking booking : bookingsEndingInOneWeek) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(booking.getUserEmail());
            message.setSubject("Booking Notification");
            message.setText("Dear " + booking.getUserEmail() + ",\n\n"
                    + "Your booking for Room : " + booking.getRoomId() + " from "
                    + booking.getStartDate().toString() + " to " + booking.getEndDate().toString()
                    + " is ending in one week.\n\n"
                    + "Thank you .\n"
                    + "-----------");
            mailSender.send(message);
            log.info("booking notification email to user {}", booking.getUserEmail());
        }
    }



    public Statistics getStatistics(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findByStartDateBetween(startDate, endDate);
        Long numBookings = (long) bookings.size();
        Double totalRevenue = bookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();
        Double avgRevenuePerBooking = numBookings > 0 ? totalRevenue / numBookings : 0;
        Map<String, Long> numBookingsPerRoom = bookings.stream()
                .collect(Collectors.groupingBy(Booking::getRoomId, Collectors.counting()));
        Map<String, Long> numBookingsPerUser = bookings.stream()
                .collect(Collectors.groupingBy(Booking::getUserEmail, Collectors.counting()));
        return new Statistics(numBookings, totalRevenue, avgRevenuePerBooking, numBookingsPerRoom, numBookingsPerUser);
    }
    @Scheduled(fixedRate = 300000)
   // @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredBookings() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Booking> expiredBookings = bookingRepository.findByEndDateBefore(yesterday);
        for (Booking booking : expiredBookings) {
            bookingRepository.delete(booking);
            updateRoomAvailability(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());
        }
    }


    @Scheduled(fixedRate = 300000)
    //@Scheduled(cron = "0 0 0 * * *")
    public void checkAutoRenewalBookings() {
        LocalDate today = LocalDate.now();
        List<Booking> autoRenewalBookings = bookingRepository.findByAutoRenewed(true);
        for (Booking booking : autoRenewalBookings) {
            LocalDate expiryDate = booking.getEndDate();
            if (expiryDate.isEqual(today)) {
                LocalDate newExpiryDate = expiryDate.plusMonths(1);
                booking.setEndDate(newExpiryDate);
                booking.setTotalPrice(calculateTotalPrice(booking.getRoomId(), booking.getStartDate(), newExpiryDate));
                bookingRepository.save(booking);
                emailService.renouvEmail(booking);
            } else if (expiryDate.minusDays(1).isEqual(today)) {
                emailService.renouvReminderEmail(booking);
            }
        }
    }

    private Double calculateTotalPrice(String roomId, LocalDate startDate, LocalDate endDate) {
        Room room = getRoomById(roomId);
        int numNights = (int) ChronoUnit.DAYS.between(startDate, endDate);
        return room.getPrice() * numNights;
    }

}
