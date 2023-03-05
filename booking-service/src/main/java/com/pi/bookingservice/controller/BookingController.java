package com.pi.bookingservice.controller;

import com.pi.bookingservice.dto.Statistics;
import com.pi.bookingservice.model.Booking;
import com.pi.bookingservice.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private JavaMailSender mailSender;
    @PostMapping("/")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking,
                                                 @RequestParam String token,
                                                 @RequestParam Double amount,
                                                 @RequestParam String currency
                                                 ) {
        try {
            Booking createdBooking = bookingService.createBooking(booking, token, amount, currency);
            return ResponseEntity.ok(createdBooking);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{bookingId}/extend")
    public Booking extendBooking(@PathVariable long bookingId,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate newEndDate,
                                 @RequestParam String token,
                                 @RequestParam Double amount,
                                 @RequestParam String currency) {
        return bookingService.extendBooking(bookingId, newEndDate, token, amount, currency);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteBooking(@PathVariable long bookingId) {
        bookingService.deleteBooking(bookingId);
    }
    @PostMapping("/send-mail")
    public ResponseEntity<String> sendEmail(@RequestBody Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(booking.getUserEmail());
        message.setSubject("Booking Confirmation - Room : " + booking.getRoomId());
        message.setText("Dear " + booking.getUserEmail() + ",\n\n"
                + "Your booking has been confirmed for Room : " + booking.getRoomId() + " from "
                + booking.getStartDate().toString() + " to " + booking.getEndDate().toString() + ".\n\n"
                + "Total price: TND" + booking.getTotalPrice() + "\n\n"
                + "Thank you .\n"
                + "-----------");

        mailSender.send(message);

        return ResponseEntity.ok("Email sent successfully!");
    }
    @GetMapping("/statistics")
    public Statistics getStatistics(@RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                    @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return bookingService.getStatistics(startDate, endDate);
    }


}



