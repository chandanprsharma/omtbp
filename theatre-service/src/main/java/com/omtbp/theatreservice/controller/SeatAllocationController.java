package com.omtbp.theatreservice.controller;

import com.omtbp.theatreservice.dto.BookRequestedSeatsDto;
import com.omtbp.theatreservice.dto.BookedSeatsDto;
import com.omtbp.theatreservice.service.SeatAllocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theatres")
@AllArgsConstructor
@CrossOrigin(origins="*")
public class SeatAllocationController {
    @Autowired
    private SeatAllocationService seatAllocationService;

    @RequestMapping(value = "s/checkRequestedSeatsAvailability", method = { RequestMethod.GET, RequestMethod.POST })
    // Cause: https://github.com/spring-cloud/spring-cloud-netflix/issues/36
    public ResponseEntity<Boolean> checkRequestedSeatsAvailability(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto){
        Boolean status = seatAllocationService.checkRequestedSeatsAvailability(bookRequestedSeatsDto);
        return new ResponseEntity<>(status, status?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @PostMapping("s/bookRequestedSeats")
    public ResponseEntity<BookedSeatsDto> bookRequestedSeats(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto){
            BookedSeatsDto bookedSeatsDto = seatAllocationService.bookRequestedSeats(bookRequestedSeatsDto);
            return new ResponseEntity<>(bookedSeatsDto,
                    bookedSeatsDto != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "s/checkNSeatsAvailability", method = { RequestMethod.GET, RequestMethod.POST })
    // Cause: https://github.com/spring-cloud/spring-cloud-netflix/issues/36
    public ResponseEntity<Boolean> checkNSeatsAvailability(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto){
        Boolean status = seatAllocationService.checkNSeatsAvailability(bookRequestedSeatsDto);
        return new ResponseEntity<>(status, status?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @PostMapping("s/bookNSeats")
    public ResponseEntity<BookedSeatsDto> bookNSeats(@RequestBody BookRequestedSeatsDto bookRequestedSeatsDto){
        BookedSeatsDto bookedSeatsDto = seatAllocationService.bookNSeats(bookRequestedSeatsDto);
        return new ResponseEntity<>(bookedSeatsDto,
                bookedSeatsDto!=null?HttpStatus.CREATED:HttpStatus.BAD_REQUEST);
    }
}

