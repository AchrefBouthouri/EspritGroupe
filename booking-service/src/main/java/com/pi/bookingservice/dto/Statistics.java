package com.pi.bookingservice.dto;

import lombok.*;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statistics  {
    private Long numBookings;
    private Double totalRevenue;
    private Double avgRevenuePerBooking;
    private Map<String, Long> numBookingsPerRoom;
    private Map<String, Long> numBookingsPerUser;

}
