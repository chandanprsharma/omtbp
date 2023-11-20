package com.omtbp.bookingservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private String resource;

    public ResourceNotFoundException(String resource) {
        super(String.format("%s not found.", resource));
        this.resource = resource;
    }
}
