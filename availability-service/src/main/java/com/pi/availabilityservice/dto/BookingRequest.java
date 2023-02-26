package com.pi.availabilityservice.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingRequest {
    private String roomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userEmail;

}
