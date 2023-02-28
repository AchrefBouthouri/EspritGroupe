package com.pi.roomservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(value = "room")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Room {
    @Id
    private String id = UUID.randomUUID().toString();
    private String roomNumber;
    private RoomType roomType;
    private String description;
    private Integer surface;
    private Double price;
}

