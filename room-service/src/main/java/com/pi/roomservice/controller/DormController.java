package com.pi.roomservice.controller;


import com.pi.roomservice.dto.DormRequest;
import com.pi.roomservice.dto.DormResponse;
import com.pi.roomservice.model.Room;
import com.pi.roomservice.service.DormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dorms")
@RequiredArgsConstructor
public class DormController {
    private final DormService dormService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDorm(@RequestBody DormRequest dormRequest) {
        dormService.createDorm(dormRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DormResponse> getAllDorms() {
        return dormService.getAllDorms();
    }

    @GetMapping("/{name}")
    public DormResponse getByName(@PathVariable String name) {
        return dormService.getByName(name);
    }

    @PutMapping("/{id}")
    public void updateDorm(@PathVariable String id, @RequestBody DormRequest dormRequest) {
        dormService.updateDorm(id, dormRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteDorm(@PathVariable String id) {
        dormService.deleteDorm(id);
    }

    @GetMapping("/{name}/rooms")
    @ResponseStatus(HttpStatus.OK)
    public List<Room> getRoomsByDormName(@PathVariable String name) {
        return dormService.getRoomsByDormName(name);
    }
}