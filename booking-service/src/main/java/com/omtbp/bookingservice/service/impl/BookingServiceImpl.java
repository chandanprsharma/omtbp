package com.omtbp.bookingservice.service.impl;

import com.omtbp.bookingservice.dto.BookRequestedSeatsDto;
import com.omtbp.bookingservice.dto.BookedSeatsDto;
import com.omtbp.bookingservice.entity.Booking;
import com.omtbp.bookingservice.entity.BookingStatus;
import com.omtbp.bookingservice.exception.BookingException;
import com.omtbp.bookingservice.exception.InvalidParameterException;
import com.omtbp.bookingservice.exception.ResourceNotFoundException;
import com.omtbp.bookingservice.external.services.TheatreService;
import com.omtbp.bookingservice.repository.BookingRepository;
import com.omtbp.bookingservice.service.BookingService;
import jakarta.persistence.LockModeType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TheatreService theatreService;
    private final Lock lock = new ReentrantLock();
    private static final Logger LOG = Logger.getLogger(BookingServiceImpl.class.getName());

    @Override
    public Boolean checkRequestedSeatsAvailability(BookRequestedSeatsDto bookRequestedSeatsDto) {
        try {
            ResponseEntity<Boolean> availability = theatreService.checkRequestedSeatsAvailability(bookRequestedSeatsDto);
            return availability.getBody();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Requested seats not found");
        }
    }

    @Override
    public Boolean checkNSeatsAvailability(BookRequestedSeatsDto bookRequestedSeatsDto) {
        try {
            ResponseEntity<Boolean> availability = theatreService.checkNSeatsAvailability(bookRequestedSeatsDto);
            return availability.getBody();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Requested seats not found");
        }
    }

    @Override
    @Transactional
    public BookedSeatsDto bookRequestedSeats(BookRequestedSeatsDto bookRequestedSeatsDto) {
        LockModeType lockMode = LockModeType.PESSIMISTIC_WRITE; // Choose the appropriate lock mode
        // Acquiring the lock
        boolean lockAcquired = lock.tryLock();
        if (!lockAcquired) {
            throw new IllegalStateException("Another thread is already executing.");
        }
        try {
            //Generate unique bookingId
            String uniqueBookingId = UUID.randomUUID().toString();
            bookRequestedSeatsDto.setBookingId(uniqueBookingId);
            ResponseEntity<BookedSeatsDto> bookedSeatsDto = theatreService.bookRequestedSeats(bookRequestedSeatsDto);
            if (bookedSeatsDto != null && bookedSeatsDto.getBody() != null) {
                Booking booking = Booking.builder()
                        .bookingId(uniqueBookingId)
                        .date(bookRequestedSeatsDto.getDate())
                        .userId(bookRequestedSeatsDto.getUserId())
                        .movieId(bookRequestedSeatsDto.getMovieId())
                        .theaterId(bookRequestedSeatsDto.getTheaterId())
                        .showTime(bookRequestedSeatsDto.getShowTime())
                        .seatType(bookRequestedSeatsDto.getSeatType())
                        .price(bookedSeatsDto.getBody().getTotalPrice())
                        .discount(bookedSeatsDto.getBody().getDiscount())
                        .bookingStatus(BookingStatus.BOOKED)
                        .isActive(Boolean.TRUE).build();
                bookingRepository.save(booking);
                return bookedSeatsDto.getBody();
            } else
                throw new InvalidParameterException("Unable to book the seats.");
        } catch (Exception e) {
            throw new BookingException("Unable to book the seats. " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public BookedSeatsDto bookNSeats(BookRequestedSeatsDto bookRequestedSeatsDto) {
        LockModeType lockMode = LockModeType.PESSIMISTIC_WRITE; // Choose the appropriate lock mode
        // Acquiring the lock
        boolean lockAcquired = lock.tryLock();
        if (!lockAcquired) {
            throw new IllegalStateException("Another thread is already executing.");
        }
        try {
            //Generate unique bookingId
            String uniqueBookingId = UUID.randomUUID().toString();
            bookRequestedSeatsDto.setBookingId(uniqueBookingId);
            ResponseEntity<BookedSeatsDto> bookedSeatsDto = theatreService.bookNSeats(bookRequestedSeatsDto);
            if (bookedSeatsDto != null && bookedSeatsDto.getBody() != null) {
                Booking booking = Booking.builder()
                        .bookingId(uniqueBookingId)
                        .date(bookRequestedSeatsDto.getDate())
                        .userId(bookRequestedSeatsDto.getUserId())
                        .movieId(bookRequestedSeatsDto.getMovieId())
                        .theaterId(bookRequestedSeatsDto.getTheaterId())
                        .showTime(bookRequestedSeatsDto.getShowTime())
                        .seatType(bookRequestedSeatsDto.getSeatType())
                        .price(bookedSeatsDto.getBody().getTotalPrice())
                        .discount(bookedSeatsDto.getBody().getDiscount())
                        .bookingStatus(BookingStatus.BOOKED)
                        .isActive(Boolean.TRUE).build();
                bookingRepository.save(booking);
                return bookedSeatsDto.getBody();
            } else
                throw new InvalidParameterException("Unable to book the seats.");
        } catch (Exception e) {
            throw new BookingException("Unable to book the seats. " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
