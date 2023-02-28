package com.pi.roomservice.dto;

import com.pi.roomservice.model.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {

    private String roomNumber;
    private RoomType roomType;
    private String description;
    private Integer surface;
    private Double price;
}

