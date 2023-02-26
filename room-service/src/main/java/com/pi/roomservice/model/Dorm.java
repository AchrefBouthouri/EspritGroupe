package com.pi.roomservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(value = "dorm")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Dorm {
    @Id
    private String id;
    private String name;
    private String address;
    @JsonIgnore
    private List<Room> rooms = new ArrayList<>();
}
