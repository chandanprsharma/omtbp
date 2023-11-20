package com.omtbp.theatreservice.repository;

import com.omtbp.theatreservice.entity.SeatAllocation;
import com.omtbp.theatreservice.entity.SeatType;
import com.omtbp.theatreservice.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SeatAllocationRepository extends JpaRepository<SeatAllocation, String> {

    @Query(value = "SELECT * FROM seat_allocations sa " +
            "WHERE sa.theater_id = :theater_id AND sa.movie_id = :movie_id AND sa.screen_id = :screen_id " +
            "AND sa.seat_id IN (:seat_ids) AND sa.date = :date AND sa.show_time = :show_time " +
            "AND sa.is_locked = false AND sa.is_occupied = false", nativeQuery = true)
    List<SeatAllocation> findRequestedSeatsAvailability(
            @Param("theater_id") String theaterId,
            @Param("movie_id") String movieId,
            @Param("screen_id") Long screenId,
            @Param("seat_ids") List<Long> seatIds,
            @Param("date") Date date,
            @Param("show_time") Integer showTime);

    @Query(value = "SELECT * FROM seat_allocations sa " +
            "WHERE sa.theater_id = :theater_id AND sa.movie_id = :movie_id AND sa.screen_id = :screen_id " +
            "AND sa.seat_type = :seat_type AND sa.date = :date AND sa.show_time = :show_time " +
            "AND sa.is_occupied = false LIMIT :num_of_seats", nativeQuery = true)
    List<SeatAllocation> findNAvailableSeats(
            @Param("theater_id") String theaterId,
            @Param("movie_id") String movieId,
            @Param("screen_id") Long screenId,
            @Param("seat_type") Integer seatType,
            @Param("date") Date date,
            @Param("show_time") Integer showTime,
            @Param("num_of_seats") Integer N);
}
