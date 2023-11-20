package com.omtbp.bookingservice.external.entity;

import com.omtbp.bookingservice.dto.ShowTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SeatAllocation {

    private Long allocationId;
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
