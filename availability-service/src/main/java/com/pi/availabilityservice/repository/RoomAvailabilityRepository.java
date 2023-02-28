package com.pi.availabilityservice.repository;

import com.pi.availabilityservice.model.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {


    List<RoomAvailability> findByRoomId(String roomId);
    List<RoomAvailability> findByRoomIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(String roomId, LocalDate endDate, LocalDate startDate);
    RoomAvailability findByRoomIdAndStartDateAndEndDate(String roomId, LocalDate startDate, LocalDate endDate);

    Object findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);

    List<RoomAvailability> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);
}

