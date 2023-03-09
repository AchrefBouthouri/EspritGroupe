package com.pi.bookingservice.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;
    private String roomId;
    private LocalDate startDate;
    @NotNull(message = "add end date")
    @Future(message = "End date is in future")
    private LocalDate endDate;
    @Email(message = "Invalid email")
    @NotNull(message = "Add your email")
    private String userEmail;
    private Double TotalPrice;
    private boolean Paid;
    private boolean autoRenewed;
}
