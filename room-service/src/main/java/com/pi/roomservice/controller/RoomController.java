package com.pi.roomservice.controller;


import com.pi.roomservice.dto.RoomAvailability;
import com.pi.roomservice.dto.RoomRequest;
import com.pi.roomservice.dto.RoomResponse;
import com.pi.roomservice.service.RoomService;
import com.pi.roomservice.model.Room;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRoom(@RequestBody RoomRequest roomRequest) {
        roomService.createRoom(roomRequest);
    }

    @PostMapping("/{name}/rooms")
    public RoomResponse createRoomInDorm(@PathVariable("name") String name, @RequestBody RoomRequest roomRequest) {
        Room room = roomService.createRoomInDorm(name, roomRequest);

        return roomService.mapToRoomResponse(room);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PutMapping("/{roomNumber}")
    public void updateRoom(@PathVariable String roomNumber, @RequestBody RoomRequest roomRequest) {
        roomService.updateRoom(roomNumber, roomRequest);
    }

    @DeleteMapping("/{roomNumber}")
    public void deleteRoom(@PathVariable String roomNumber) {
        roomService.deleteRoom(roomNumber);
    }

    @GetMapping("/rooms/{roomNumber}")
    public ResponseEntity<RoomResponse> getRoomByNumber(@PathVariable String roomNumber) {
        RoomResponse roomResponse = roomService.getRoomByNumber(roomNumber);
        if (roomResponse != null) {
            return ResponseEntity.ok(roomResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable String id) {
        RoomResponse roomResponse = roomService.getRoomById(id);
        if (roomResponse != null) {
            return ResponseEntity.ok(roomResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam String roomId,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(roomService.checkAvailability(roomId, startDate, endDate));
    }
}



