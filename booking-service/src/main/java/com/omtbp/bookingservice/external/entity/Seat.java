package com.omtbp.bookingservice.external.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Seat {

    private Long seatId;
    private String seatLabel;
    private SeatType seatType;
    private Integer price;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Screen screen;
}
