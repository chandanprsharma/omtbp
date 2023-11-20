package com.omtbp.theatreservice.service;

import com.omtbp.theatreservice.dto.BookRequestedSeatsDto;
import com.omtbp.theatreservice.dto.BookedSeatsDto;
import com.omtbp.theatreservice.dto.SeatAllocationDto;

public interface SeatAllocationService {
    Boolean allotMovieSeat(SeatAllocationDto seatAllocationDto);
    Boolean checkRequestedSeatsAvailability(BookRequestedSeatsDto bookRequestedSeatsDto);
    Boolean checkNSeatsAvailability(BookRequestedSeatsDto bookRequestedSeatsDto);
    BookedSeatsDto bookRequestedSeats(BookRequestedSeatsDto bookRequestedSeatsDto);
    BookedSeatsDto bookNSeats(BookRequestedSeatsDto bookRequestedSeatsDto);
}