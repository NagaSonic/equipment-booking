package com.training.booking.service;

import java.util.UUID;
import com.training.booking.dto.request.CreateBookingRequest;
import com.training.booking.dto.response.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(CreateBookingRequest request);
    BookingResponse getBooking(UUID id);
}
