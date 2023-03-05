package com.pi.bookingservice.repository;

import com.pi.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface BookingRepository  extends JpaRepository<Booking, Long> {
    List<Booking> findByEndDateBetween(LocalDate start, LocalDate end);
    List<Booking> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    List<Booking> findByEndDateBefore(LocalDate yesterday);
    List<Booking> findByAutoRenewedAndEndDateBetween(boolean autoRenewed, LocalDate startDate, LocalDate endDate);

    List<Booking> findByAutoRenewed(boolean autoRenewed);


}
