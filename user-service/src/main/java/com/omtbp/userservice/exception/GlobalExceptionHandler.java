package com.omtbp.userservice.exception;

import com.omtbp.userservice.service.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ApiResponse> invalidParameterException(InvalidParameterException e) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse> invalidCredentialsException(InvalidCredentialsException e) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiResponse> invalidTokenException(InvalidTokenException e) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .success(false)
                .status(HttpStatus.UNAUTHORIZED).build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> usernameNotFoundException(UsernameNotFoundException e) {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
