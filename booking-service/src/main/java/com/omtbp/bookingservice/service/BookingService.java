package com.omtbp.bookingservice.service;


import com.omtbp.bookingservice.dto.BookRequestedSeatsDto;
import com.omtbp.bookingservice.dto.BookedSeatsDto;
import com.omtbp.bookingservice.dto.ShowTime;
import com.omtbp.bookingservice.entity.Booking;

import java.util.Date;
import java.util.List;

public interface BookingService {
    /*List<Booking> getByUserId(Long userId);
    List<Booking> getByUserIdAndMovieId(Long userId, String movieId);
    List<Booking> getByUserIdAndMovieIdAndDate(Long userId, String movieId, Date date);
    List<Booking> getByTheaterIdAndMovieId(String theaterId, String movieId);
    List<Booking> getByTheaterIdAndMovieIdAndDate(String theaterId, String movieId, Date date);
    List<Booking> getByTheaterIdAndMovieIdAndDateAndShowTime(String theaterId, String movieId, Date date, ShowTime showTime);*/
    Boolean checkRequestedSeatsAvailability(BookRequestedSeatsDto bookRequestedSeatsDto);
    Boolean checkNSeatsAvailability(BookRequestedSeatsDto bookRequestedSeatsDto);
    BookedSeatsDto bookRequestedSeats(BookRequestedSeatsDto bookRequestedSeatsDto);
    BookedSeatsDto bookNSeats(BookRequestedSeatsDto bookRequestedSeatsDto);
}