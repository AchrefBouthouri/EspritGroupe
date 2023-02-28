package com.pi.bookingservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class Room {
    private String id;
    private String roomNumber;
    private String roomType;
    private String description;
    private Integer surface;
    private Double price;
}
