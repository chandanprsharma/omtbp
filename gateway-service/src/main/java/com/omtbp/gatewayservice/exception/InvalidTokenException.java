package com.omtbp.gatewayservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenException extends ResponseStatusException {

    public InvalidTokenException(HttpStatus status) {
        super(status);
    }
}