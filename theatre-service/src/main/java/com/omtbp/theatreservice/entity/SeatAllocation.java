package com.omtbp.theatreservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Seat_allocations")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SeatAllocation {

    @Id
    private String allocationId;
    private Date date;
    private Long seatId;
    private String seatLabel;
    private SeatType seatType;
    private Integer price;
    private Long screenId;
    private String theaterId;
    private String movieId;
    private Long userId;
    private ShowTime showTime;
    private Integer discountInPercent;
    private Boolean isLocked;
    private LocalDateTime lockedTime;
    private String bookingId;
    private Boolean isOccupied;

}
