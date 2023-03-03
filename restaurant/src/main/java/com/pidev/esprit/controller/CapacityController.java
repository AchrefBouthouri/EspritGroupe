package com.pidev.esprit.controller;
import com.pidev.esprit.model.Capacity;
import com.pidev.esprit.model.Menu;
import com.pidev.esprit.service.CapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/capacity")
public class CapacityController {

    @Autowired
    private CapacityService capacityService;

    @GetMapping("/{id}")
    public ResponseEntity<Capacity> getCapacityById(@PathVariable Long id) {
        Capacity capacity = capacityService.getCapacityById(id);
        if (capacity != null) {
            return new ResponseEntity<>(capacity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping("/")
    public ResponseEntity<Capacity> createCapacity(@RequestParam int value) {
        Capacity capacity = capacityService.addCapacity(value);
        return new ResponseEntity<>(capacity, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCapacityById(@PathVariable Long id) {
        capacityService.deleteCapacityById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}







