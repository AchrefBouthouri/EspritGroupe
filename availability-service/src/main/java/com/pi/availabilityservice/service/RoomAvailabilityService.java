package com.pi.availabilityservice.service;

import com.pi.availabilityservice.model.RoomAvailability;
import com.pi.availabilityservice.repository.RoomAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomAvailabilityService {
    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;
    private final WebClient.Builder webClientBuilder;

public boolean checkRoomAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
    List<RoomAvailability> roomAvailabilities = roomAvailabilityRepository.findByRoomId(roomId);
    for (RoomAvailability roomAvailability : roomAvailabilities) {
        if (startDate.isBefore(roomAvailability.getEndDate()) && endDate.isAfter(roomAvailability.getStartDate())) {
            return false;
        }
    }
    return true;
}

    public void updateRoomAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        List<RoomAvailability> roomAvailabilities = roomAvailabilityRepository.findByRoomId(roomId);
        List<RoomAvailability> updatedRoomAvailabilities = new ArrayList<>();

        for (RoomAvailability roomAvailability : roomAvailabilities) {
            if (startDate.isBefore(roomAvailability.getEndDate()) && endDate.isAfter(roomAvailability.getStartDate())) {

                if (startDate.isAfter(roomAvailability.getStartDate())) {
                    updatedRoomAvailabilities.add(RoomAvailability.builder()
                            .roomId(roomAvailability.getRoomId())
                            .startDate(roomAvailability.getStartDate())
                            .endDate(startDate.minusDays(1))
                            .build());
                }
                if (endDate.isBefore(roomAvailability.getEndDate())) {
                    updatedRoomAvailabilities.add(RoomAvailability.builder()
                            .roomId(roomAvailability.getRoomId())
                            .startDate(endDate.plusDays(1))
                            .endDate(roomAvailability.getEndDate())
                            .build());
                }
            } else {
                updatedRoomAvailabilities.add(roomAvailability);
            }
        }
        updatedRoomAvailabilities.add(RoomAvailability.builder()
                .roomId(roomId)
                .startDate(startDate)
                .endDate(endDate)
                .build());

        roomAvailabilityRepository.deleteAll(roomAvailabilities);
        roomAvailabilityRepository.saveAll(updatedRoomAvailabilities);
    }
}