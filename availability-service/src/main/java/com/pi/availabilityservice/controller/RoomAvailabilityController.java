package com.pi.availabilityservice.controller;

import com.pi.availabilityservice.dto.AvailabilityRequest;
import com.pi.availabilityservice.dto.BookingRequest;
import com.pi.availabilityservice.model.RoomAvailability;
import com.pi.availabilityservice.repository.RoomAvailabilityRepository;
import com.pi.availabilityservice.service.RoomAvailabilityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/availability")
public class RoomAvailabilityController {

    @Autowired
    private RoomAvailabilityService roomAvailabilityService;
//
//    @GetMapping("/check")
//    public boolean checkRoomAvailability(@RequestParam String roomId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
//        return roomAvailabilityService.checkRoomAvailability(roomId, startDate, endDate);
//    }
@GetMapping("/check")
public boolean checkRoomAvailability(@RequestParam String roomId,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
    return roomAvailabilityService.checkRoomAvailability(roomId, startDate, endDate);
}

    @PutMapping("/update")
    public void updateRoomAvailability(@RequestParam String roomId,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        roomAvailabilityService.updateRoomAvailability(roomId, startDate, endDate);
    }
}
