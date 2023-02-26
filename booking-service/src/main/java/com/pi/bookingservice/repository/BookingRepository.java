package com.pi.bookingservice.repository;

import com.pi.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository  extends JpaRepository<Booking, Long> {
    List<Booking> findByRoomId(String roomId);

    List<Booking> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate, LocalDate endDate);

    List<Booking> findByUserEmail(String userEmail);
}
