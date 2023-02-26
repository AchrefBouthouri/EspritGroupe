package com.pi.bookingservice.service;

import com.pi.bookingservice.model.Booking;
import com.pi.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookingService {
    @Autowired
    private RestTemplate restTemplate;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    private BookingRepository bookingRepository;
public Booking createBooking(Booking booking) {
    boolean isRoomAvailable = checkRoomAvailability(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());
    if (!isRoomAvailable) {
        throw new RuntimeException("The room is not available ");
    }

    // Update room availability
    updateRoomAvailability(booking.getRoomId(), booking.getStartDate(), booking.getEndDate());

    return bookingRepository.save(booking);
}

    private boolean checkRoomAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        String url = "http://availability-service/api/availability/check?roomId={roomId}&startDate={startDate}&endDate={endDate}";
        Boolean isAvailable = webClientBuilder.build()
                .get()
                .uri(url, roomId, startDate, endDate)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return isAvailable;
    }

    private void updateRoomAvailability(String roomId, LocalDate startDate, LocalDate endDate) {
        String url = "http://availability-service/api/availability/update?roomId={roomId}&startDate={startDate}&endDate={endDate}";
        webClientBuilder.build()
                .put()
                .uri(url, roomId, startDate, endDate)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}