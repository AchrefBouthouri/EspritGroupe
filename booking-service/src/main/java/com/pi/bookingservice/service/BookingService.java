package com.pi.bookingservice.service;

import com.pi.bookingservice.dto.Room;
import com.pi.bookingservice.model.Booking;
import com.pi.bookingservice.repository.BookingRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static com.stripe.Stripe.apiKey;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookingService {
    @Autowired
    //private RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;
    @Autowired
    private BookingRepository bookingRepository;

    @Value("${paiement-service.url}")
    private String paymentServiceUrl;
public Booking createBooking(Booking booking, String token, Double amount, String currency) {
    boolean isRoomAvailable = checkRoomAvailability(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());
    if (!isRoomAvailable) {
        throw new RuntimeException("The room is not available ");
    }
    // Calculate total price
    Room room = getRoomById(booking.getRoomId());
    int numNights = (int) ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
    Double totalPrice = room.getPrice() * numNights;

    // Update booking details
    booking.setTotalPrice(totalPrice);
    // Check payment
    try {
        chargeCreditCard(token, totalPrice, currency);
    } catch (Exception e) {
        throw new RuntimeException("Payment not completed");
    }
    // Update room availability
    updateRoomAvailability(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());
    return bookingRepository.save(booking);
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
}