package com.omtbp.bookingservice.controller;

import com.omtbp.bookingservice.dto.BookRequestedSeatsDto;
import com.omtbp.bookingservice.dto.BookedSeatsDto;
import com.omtbp.bookingservice.service.BookingService;
import com.omtbp.bookingservice.service.payload.ApiResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor
@CrossOrigin(origins="*")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    private static final Logger LOG = Logger.getLogger(BookingController.class.getName());

    @GetMapping("checkRequestedSeatsAvailability")
    @CircuitBreaker(name = "theatreServiceBreaker", fallbackMethod = "theatreServiceFallback")
    public ResponseEntity<Boolean> checkRequestedSeatsAvailability(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto){
        Boolean status = bookingService.checkRequestedSeatsAvailability(bookRequestedSeatsDto);
        return new ResponseEntity<>(status, status?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @PostMapping("bookRequestedSeats")
    @CircuitBreaker(name = "theatreServiceBreaker", fallbackMethod = "theatreServiceFallback")
    public ResponseEntity<BookedSeatsDto> bookRequestedSeats(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto){
        BookedSeatsDto bookedSeatsDto = bookingService.bookRequestedSeats(bookRequestedSeatsDto);
        return new ResponseEntity<>(bookedSeatsDto,
                bookedSeatsDto!=null?HttpStatus.CREATED:HttpStatus.BAD_REQUEST);
    }

    @GetMapping("checkNSeatsAvailability")
    @CircuitBreaker(name = "theatreServiceBreaker", fallbackMethod = "theatreServiceFallback")
    public ResponseEntity<Boolean> checkNSeatsAvailability(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto){
        Boolean status = bookingService.checkNSeatsAvailability(bookRequestedSeatsDto);
        return new ResponseEntity<>(status, status?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @PostMapping("bookNSeats")
    @CircuitBreaker(name = "theatreServiceBreaker", fallbackMethod = "theatreServiceFallback")
    public ResponseEntity<BookedSeatsDto> bookNSeats(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto){
        BookedSeatsDto bookedSeatsDto = bookingService.bookNSeats(bookRequestedSeatsDto);
        return new ResponseEntity<>(bookedSeatsDto,
                bookedSeatsDto!=null? HttpStatus.CREATED:HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ApiResponse> theatreServiceFallback(BookRequestedSeatsDto bookRequestedSeatsDto, Exception e) {
        LOG.severe("Fallback is executed because Theatre service is down: " + e.getMessage());
        ApiResponse apiResponse = ApiResponse.builder().message("Theatre service down. Please try after sometime.")
                .success(false)
                .status(HttpStatus.BAD_GATEWAY).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_GATEWAY);
    }
}