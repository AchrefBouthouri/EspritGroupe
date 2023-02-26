package com.pi.roomservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DormRequest {

    private String name;
    private String address;
    @JsonIgnore
    private List<RoomRequest> rooms;
}
