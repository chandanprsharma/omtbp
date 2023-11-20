package com.omtbp.theatreservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.omtbp.theatreservice.entity.Seat;
import com.omtbp.theatreservice.entity.SeatType;
import com.omtbp.theatreservice.entity.ShowTime;
import lombok.*;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BookRequestedSeatsDto implements Serializable {

    private static final long serialVersionUID=1L;
    private String theaterId;
    private String movieId;
    private Long screenId;
    private Long userId;
    private Integer numOfSeats;
    private List<Long> seatIds;
    private SeatType seatType;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date date;
    private String bookingId;
    private ShowTime showTime;

}
