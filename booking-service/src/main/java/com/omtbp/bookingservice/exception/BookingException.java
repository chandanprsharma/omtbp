package com.omtbp.bookingservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingException extends RuntimeException {
    private String msg;

    public BookingException() {
        super("Another thread is already executing.");
        this.msg = "Another thread is already executing.";
    }

    public BookingException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
