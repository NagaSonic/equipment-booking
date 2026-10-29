package com.training.booking.dto.response;

import java.time.LocalDate;
import java.util.UUID;
import com.training.booking.entity.BookingStatus;

public record BookingResponse(UUID id, String instructorName, LocalDate bookingDate, Integer quantity, String purpose, BookingStatus status) {}
