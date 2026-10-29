package com.training.booking.exception;

import java.time.LocalDate;

public class InsufficientAvailabilityException extends RuntimeException {
    public InsufficientAvailabilityException(LocalDate date, int requested, int available) {
        super("Only " + available + " laptop(s) are available for " + date + "; requested " + requested + ".");
    }
}
