package com.training.booking.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.ResponseEntity;
import com.training.booking.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex) {
        Map<String, String> details = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> details.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return response(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Request validation failed.", details);
    }
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> badInput(Exception ex) {
        return response(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "Request body, date or identifier has an invalid format.", Map.of());
    }
    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(BookingNotFoundException ex) {
        return response(HttpStatus.NOT_FOUND, "BOOKING_NOT_FOUND", ex.getMessage(), Map.of());
    }
    @ExceptionHandler(InsufficientAvailabilityException.class)
    public ResponseEntity<ErrorResponse> unavailable(InsufficientAvailabilityException ex) {
        return response(HttpStatus.CONFLICT, "INSUFFICIENT_AVAILABILITY", ex.getMessage(), Map.of());
    }
    @ExceptionHandler(InvalidBookingDateException.class)
    public ResponseEntity<ErrorResponse> invalidDate(InvalidBookingDateException ex) {
        return response(HttpStatus.BAD_REQUEST, "INVALID_BOOKING_DATE", ex.getMessage(), Map.of());
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> integrity(DataIntegrityViolationException ex) {
        return response(HttpStatus.BAD_REQUEST, "DATA_INTEGRITY_ERROR", "The request violates a data constraint.", Map.of());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unexpected(Exception ex) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "An unexpected error occurred.", Map.of());
    }
    private ResponseEntity<ErrorResponse> response(HttpStatus status, String code, String message, Map<String, String> details) {
        return ResponseEntity.status(status).body(new ErrorResponse(code, message, Instant.now(), details));
    }
}
