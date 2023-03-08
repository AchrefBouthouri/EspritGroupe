package com.pi.availabilityservice.repository;

import com.pi.availabilityservice.model.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {


    List<RoomAvailability> findByRoomId(String roomId);

}

