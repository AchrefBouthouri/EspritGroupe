package com.pi.availabilityservice.dto;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AvailabilityRequest {
    private String roomId;
    private LocalDate startDate;
    private LocalDate endDate;
}
