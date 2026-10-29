package com.training.booking.exception;

public class InvalidBookingDateException extends RuntimeException {
    public InvalidBookingDateException() { super("bookingDate must be today or later."); }
}
