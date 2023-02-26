package com.pi.roomservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DormResponse {
    private String id;
    private String name;
    private String address;
    private List<RoomResponse> rooms;
}
