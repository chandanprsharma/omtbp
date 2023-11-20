package com.omtbp.theatreservice.service.impl;

import com.omtbp.theatreservice.config.ConfigUtility;
import com.omtbp.theatreservice.dto.BookRequestedSeatsDto;
import com.omtbp.theatreservice.dto.BookedSeatsDto;
import com.omtbp.theatreservice.dto.SeatAllocationDto;
import com.omtbp.theatreservice.entity.*;
import com.omtbp.theatreservice.exception.BookingException;
import com.omtbp.theatreservice.repository.SeatAllocationRepository;
import com.omtbp.theatreservice.service.SeatAllocationService;
import com.omtbp.theatreservice.service.TheatreService;
import jakarta.persistence.LockModeType;

import java.util.logging.Logger;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class SeatAllocationServiceImpl implements SeatAllocationService {
    @Autowired
    private SeatAllocationRepository seatAllocationRepository;
    @Autowired
    @Lazy
    private TheatreService theatreService;
    @Autowired
    private ConfigUtility configUtil;
    private final Lock lock = new ReentrantLock();
    private static final Logger LOG = Logger.getLogger(SeatAllocationServiceImpl.class.getName());

    @Override
    @Transactional
    public Boolean allotMovieSeat(SeatAllocationDto seatAllocationDto) {
        Set<Seat> seats = seatAllocationDto.getSeats();

        long numOfDays = (((seatAllocationDto.getToDate().getTime() - seatAllocationDto.getFromDate().getTime()) / (1000 * 60 * 60 * 24)) % 365) + 1;
        List<SeatAllocation> seatAllocationList = new ArrayList<SeatAllocation>();
        Calendar cal = Calendar.getInstance();
        Theatre theatre = theatreService.getTheatreById(seatAllocationDto.getTheatreId());
        for (int i = 0; i < numOfDays; i++) {
            cal.setTime(seatAllocationDto.getFromDate());
            cal.add(Calendar.DATE, i);
            for (Seat seat : seats) {
                if (theatre.getMidDay()) {
                    SeatAllocation seatAllocation = buildSeatAllocation(seat, cal.getTime(), seatAllocationDto);
                    seatAllocation.setShowTime(ShowTime.MID_DAY);
                    seatAllocation.setDiscountInPercent(Integer.parseInt(configUtil.getProperty("com.omtbp.ticket.discount.mid-day")));
                    seatAllocationList.add(seatAllocation);
                }
                if (theatre.getEve()) {
                    SeatAllocation seatAllocation = buildSeatAllocation(seat, cal.getTime(), seatAllocationDto);
                    seatAllocation.setShowTime(ShowTime.EVENING);
                    seatAllocationList.add(seatAllocation);
                }
                if (theatre.getNight()) {
                    SeatAllocation seatAllocation = buildSeatAllocation(seat, cal.getTime(), seatAllocationDto);
                    seatAllocation.setShowTime(ShowTime.NIGHT);
                    seatAllocationList.add(seatAllocation);
                }
            }
        }
        seatAllocationRepository.saveAll(seatAllocationList);
        return Boolean.TRUE;
    }

    @Override
    public Boolean checkRequestedSeatsAvailability(BookRequestedSeatsDto bookRequestedSeatsDto) {
        List<SeatAllocation> availAllocatedSeats = seatAllocationRepository.findRequestedSeatsAvailability(
                bookRequestedSeatsDto.getTheaterId(),
                bookRequestedSeatsDto.getMovieId(),
                bookRequestedSeatsDto.getScreenId(),
                bookRequestedSeatsDto.getSeatIds(),
                bookRequestedSeatsDto.getDate(),
                bookRequestedSeatsDto.getShowTime().ordinal());
        if (null != availAllocatedSeats && availAllocatedSeats.size() == bookRequestedSeatsDto.getSeatIds().size()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
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
            BookedSeatsDto bookedSeatsDto = new BookedSeatsDto();
            int numOfSeats = bookRequestedSeatsDto.getSeatIds().size();
            List<SeatAllocation> availAllocatedSeats = seatAllocationRepository.findRequestedSeatsAvailability(
                    bookRequestedSeatsDto.getTheaterId(),
                    bookRequestedSeatsDto.getMovieId(),
                    bookRequestedSeatsDto.getScreenId(),
                    bookRequestedSeatsDto.getSeatIds(),
                    bookRequestedSeatsDto.getDate(),
                    bookRequestedSeatsDto.getShowTime().ordinal());
            if (null != availAllocatedSeats && availAllocatedSeats.size() == numOfSeats) {
                int count = 0;
                int totalCost = 0;
                int totalDiscount = 0;
                List<SeatAllocation> updatedAllocatedSeats = new ArrayList<SeatAllocation>();
                for (SeatAllocation seatAllocation : availAllocatedSeats) {
                    count += 1;
                    if (seatAllocation.getDiscountInPercent() != null && seatAllocation.getDiscountInPercent() > 0) {
                        totalDiscount += (seatAllocation.getPrice() * (seatAllocation.getDiscountInPercent() / 100f));
                    } else if (count % Integer.parseInt(configUtil.getProperty("com.omtbp.ticket.discount.eligible.limit")) == 0) {
                        totalDiscount += (seatAllocation.getPrice() *
                                (Integer.parseInt(configUtil.getProperty("com.omtbp.ticket.discount.eligible-percent")) / 100f));
                    }
                    totalCost += seatAllocation.getPrice();
                    if (!bookRequestedSeatsDto.getBookingId().isBlank())
                        seatAllocation.setBookingId(bookRequestedSeatsDto.getBookingId());
                    seatAllocation.setIsOccupied(Boolean.TRUE);
                    seatAllocation.setIsLocked(Boolean.TRUE);
                    seatAllocation.setUserId(bookRequestedSeatsDto.getUserId());
                    updatedAllocatedSeats.add(seatAllocation);
                }
                seatAllocationRepository.saveAll(updatedAllocatedSeats);
                bookRequestedSeatsDto.setNumOfSeats(numOfSeats);
                bookedSeatsDto.setBookingDetails(bookRequestedSeatsDto);
                if (!bookRequestedSeatsDto.getBookingId().isBlank())
                    bookedSeatsDto.setBookingId(bookRequestedSeatsDto.getBookingId());
                bookedSeatsDto.setTotalPrice(Long.valueOf(totalCost));
                bookedSeatsDto.setDiscount(Long.valueOf(totalDiscount));
                bookedSeatsDto.setNetPrice(Long.valueOf(totalCost - totalDiscount));
            }
            return bookedSeatsDto;
        } catch (Exception e) {
            LOG.severe("InterruptedException occurred: " + e.getMessage());
            throw new BookingException(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Boolean checkNSeatsAvailability(BookRequestedSeatsDto bookRequestedSeatsDto) {
        List<SeatAllocation> availAllocatedSeats = seatAllocationRepository.findNAvailableSeats(
                bookRequestedSeatsDto.getTheaterId(),
                bookRequestedSeatsDto.getMovieId(),
                bookRequestedSeatsDto.getScreenId(),
                bookRequestedSeatsDto.getSeatType().ordinal(),
                bookRequestedSeatsDto.getDate(),
                bookRequestedSeatsDto.getShowTime().ordinal(),
                bookRequestedSeatsDto.getNumOfSeats());
        if (null != availAllocatedSeats && availAllocatedSeats.size() == bookRequestedSeatsDto.getNumOfSeats()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
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
            BookedSeatsDto bookedSeatsDto = new BookedSeatsDto();
            int numOfSeats = bookRequestedSeatsDto.getNumOfSeats();
            List<SeatAllocation> availAllocatedSeats = seatAllocationRepository.findNAvailableSeats(
                    bookRequestedSeatsDto.getTheaterId(),
                    bookRequestedSeatsDto.getMovieId(),
                    bookRequestedSeatsDto.getScreenId(),
                    bookRequestedSeatsDto.getSeatType().ordinal(),
                    bookRequestedSeatsDto.getDate(),
                    bookRequestedSeatsDto.getShowTime().ordinal(),
                    bookRequestedSeatsDto.getNumOfSeats());
            if (null != availAllocatedSeats && availAllocatedSeats.size() == numOfSeats) {
                int count = 0;
                int totalCost = 0;
                int totalDiscount = 0;
                List<SeatAllocation> updatedAllocatedSeats = new ArrayList<SeatAllocation>();
                List<Long> seatIds = new ArrayList<Long>();
                for (SeatAllocation seatAllocation : availAllocatedSeats) {
                    count += 1;
                    if (seatAllocation.getDiscountInPercent() != null && seatAllocation.getDiscountInPercent() > 0)
                        totalDiscount += (seatAllocation.getPrice() * (seatAllocation.getDiscountInPercent() / 100f));
                    else if (count % Integer.parseInt(configUtil.getProperty("com.omtbp.ticket.discount.eligible.limit")) == 0)
                        totalDiscount += (seatAllocation.getPrice() *
                                (Integer.parseInt(configUtil.getProperty("com.omtbp.ticket.discount.eligible-percent")) / 100f));
                    totalCost += seatAllocation.getPrice();
                    seatAllocation.setIsOccupied(Boolean.TRUE);
                    seatAllocation.setIsLocked(Boolean.TRUE);
                    seatAllocation.setUserId(bookRequestedSeatsDto.getUserId());
                    seatIds.add(seatAllocation.getSeatId());
                    if (!bookRequestedSeatsDto.getBookingId().isBlank())
                        seatAllocation.setBookingId(bookRequestedSeatsDto.getBookingId());
                    updatedAllocatedSeats.add(seatAllocation);
                }
                seatAllocationRepository.saveAll(updatedAllocatedSeats);
                bookRequestedSeatsDto.setNumOfSeats(numOfSeats);
                bookRequestedSeatsDto.setSeatIds(seatIds);
                bookedSeatsDto.setBookingDetails(bookRequestedSeatsDto);
                if (!bookRequestedSeatsDto.getBookingId().isBlank())
                    bookedSeatsDto.setBookingId(bookRequestedSeatsDto.getBookingId());
                bookedSeatsDto.setTotalPrice(Long.valueOf(totalCost));
                bookedSeatsDto.setDiscount(Long.valueOf(totalDiscount));
                bookedSeatsDto.setNetPrice(Long.valueOf(totalCost - totalDiscount));
            }
            return bookedSeatsDto;
        } catch (Exception e) {
            LOG.severe("InterruptedException occurred: " + e.getMessage());
            throw new BookingException(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    private SeatAllocation buildSeatAllocation(Seat seat, Date date, SeatAllocationDto seatAllocationDto) {
        SeatAllocation seatAllocation = new SeatAllocation();

        //Generate unique allocationId
        String uniqueAllocationId = UUID.randomUUID().toString();
        seatAllocation.setAllocationId(uniqueAllocationId);
        seatAllocation.setDate(date);
        seatAllocation.setSeatId(seat.getSeatId());
        seatAllocation.setSeatType(seat.getSeatType());
        seatAllocation.setSeatLabel(seat.getSeatLabel());
        seatAllocation.setPrice(seat.getPrice());
        seatAllocation.setScreenId(seatAllocationDto.getScreenId());
        seatAllocation.setTheaterId(seatAllocationDto.getTheatreId());
        seatAllocation.setMovieId(seatAllocationDto.getMovieId());
        seatAllocation.setIsOccupied(Boolean.FALSE);
        seatAllocation.setIsLocked(Boolean.FALSE);
        return seatAllocation;
    }

}
