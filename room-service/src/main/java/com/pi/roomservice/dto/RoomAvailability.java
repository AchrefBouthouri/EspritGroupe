package com.pi.roomservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomAvailability {
    private String id;
    private String roomId;
    private LocalDate startDate;
    private LocalDate endDate;
}