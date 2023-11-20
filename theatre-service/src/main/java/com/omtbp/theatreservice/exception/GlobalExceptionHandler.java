package com.omtbp.theatreservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.omtbp.theatreservice.service.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        String msg = e.getMessage();
        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiResponse> invalidParameterException(InvalidParameterException e) {
        String msg = e.getMessage();
        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse> illegalStateException(IllegalStateException e) {
        String msg = e.getMessage();
        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .success(false)
                .status(HttpStatus.CONFLICT).build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<ApiResponse> bookingException(BookingException e) {
        String msg = e.getMessage();
        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
