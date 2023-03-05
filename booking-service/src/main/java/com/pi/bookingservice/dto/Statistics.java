package com.pi.bookingservice.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statistics  {
    private Long numBookings;
    private Double totalRevenue;
}
