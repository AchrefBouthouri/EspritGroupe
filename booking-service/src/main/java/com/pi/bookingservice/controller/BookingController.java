package com.pi.bookingservice.controller;

import com.pi.bookingservice.dto.Statistics;
import com.pi.bookingservice.model.Booking;
import com.pi.bookingservice.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@Validated
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
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") @Future(message = "date in the future") LocalDate newEndDate,
                                 @RequestParam @NotBlank(message = "payment required") String token,
                                 @RequestParam @Positive(message = "random > 0")Double amount,
                                 @RequestParam  @NotBlank(message = "put usd") String currency) {
        return bookingService.extendBooking(bookingId, newEndDate, token,amount, currency);
    }
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
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
    public ResponseEntity<Statistics> getStatistics(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        try {
            Statistics statistics = bookingService.getStatistics(startDate, endDate);
            return new ResponseEntity<>(statistics, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}



