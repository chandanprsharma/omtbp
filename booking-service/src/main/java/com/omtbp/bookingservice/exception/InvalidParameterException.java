package com.omtbp.bookingservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidParameterException extends RuntimeException {
    private String msg;

    public InvalidParameterException() {
        super("Invalid Parameter");
        this.msg = "Invalid Parameter";
    }

    public InvalidParameterException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
