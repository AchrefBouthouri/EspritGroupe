package com.pidev.esprit.service;

import com.pidev.esprit.model.Capacity;
import com.pidev.esprit.model.Menu;
import com.pidev.esprit.repository.CapacityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CapacityService {
    @Autowired
    private CapacityRepository capacityRepository;

    public Capacity getCapacityById(Long id) {
        return capacityRepository.findById(id).orElse(null);
    }

    public Capacity addCapacity(int value) {
        Capacity capacity = new Capacity();
        capacity.setValue(value);
        return capacityRepository.save(capacity);
    }



    public void deleteCapacityById(Long id) {
        capacityRepository.deleteById(id);
    }



}