package com.omtbp.bookingservice.external.services;


import com.omtbp.bookingservice.dto.BookRequestedSeatsDto;
import com.omtbp.bookingservice.dto.BookedSeatsDto;
import com.omtbp.bookingservice.dto.BrowseTheatreDto;
import com.omtbp.bookingservice.external.entity.Theatre;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@FeignClient(name = "THEATRE-SERVICE")
public interface TheatreService {

    @PostMapping("/api/theatres/s/checkRequestedSeatsAvailability")
    public ResponseEntity<Boolean> checkRequestedSeatsAvailability(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto);

    @PostMapping("/api/theatres/s/bookRequestedSeats")
    public ResponseEntity<BookedSeatsDto> bookRequestedSeats(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto);

    @PostMapping("/api/theatres/s/checkNSeatsAvailability")
    public ResponseEntity<Boolean> checkNSeatsAvailability(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto);

    @PostMapping("/api/theatres/s/bookNSeats")
    public ResponseEntity<BookedSeatsDto> bookNSeats(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto);
}
