package com.omtbp.bookingservice.repository;

import com.omtbp.bookingservice.dto.ShowTime;
import com.omtbp.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
	List<Booking> findByUserId(Long userId);
	List<Booking> findByUserIdAndMovieId(Long userId, String movieId);
	List<Booking> findByUserIdAndMovieIdAndDate(Long userId, String movieId, Date date);
	List<Booking> findByTheaterIdAndMovieId(String theaterId, String movieId);
	List<Booking> findByTheaterIdAndMovieIdAndDate(String theaterId, String movieId, Date date);
	List<Booking> findByTheaterIdAndMovieIdAndDateAndShowTime(String theaterId, String movieId, Date date, ShowTime showTime);
}
