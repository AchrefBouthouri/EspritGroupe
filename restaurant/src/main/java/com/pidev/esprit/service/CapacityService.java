package com.pidev.esprit.service;

import com.pidev.esprit.model.Capacity;
import com.pidev.esprit.model.Menu;
import com.pidev.esprit.repository.CapacityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
 @Slf4j
public class CapacityService {
    @Autowired
    private CapacityRepository capacityRepository;

    public Capacity getCapacityById(Long id) {
        return capacityRepository.findById(id).orElse(null);
    }

    public Capacity addCapacity(Capacity capacity) {
        if (capacityRepository.findAll()!=null){
            throw new RuntimeException("capacity already exists");
        }else {
        capacityRepository.save(capacity);
        return capacity;}



    }





    public void deleteCapacityById(Long id) {
        capacityRepository.deleteById(id);
    }
   /* @Scheduled(fixedRate = 60000)
    public void SetCpacite(){
        Capacity capacity = capacityRepository.findById(1L).get();
        capacity.setValue(100);
        capacityRepository.save(capacity);
        log.info("Saye");

    } */



}