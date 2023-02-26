package com.pi.bookingservice.controller;

import com.pi.bookingservice.model.Booking;
import com.pi.bookingservice.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

//    @PostMapping
//    public Booking createBooking(@RequestBody Booking booking) {
//        return bookingService.createBooking(booking);
//    }
@PostMapping("/")
public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
    try {
        Booking createdBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(createdBooking);
    } catch (RuntimeException ex) {
        return ResponseEntity.badRequest().body(null);
    }
}
}