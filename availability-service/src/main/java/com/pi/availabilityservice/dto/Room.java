package com.pi.availabilityservice.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Room {
    private String id;
    private String roomNumber;
//    private String roomType;
    private String description;
    private Integer surface;

}
