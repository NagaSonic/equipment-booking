package com.training.booking.mapper;

import org.springframework.stereotype.Component;
import com.training.booking.dto.request.CreateBookingRequest;
import com.training.booking.dto.response.BookingResponse;
import com.training.booking.entity.Booking;
import com.training.booking.entity.BookingStatus;

@Component
public class BookingMapper {
    public Booking toEntity(CreateBookingRequest request) {
        return new Booking(request.instructorName().trim(), request.bookingDate(), request.quantity(), request.purpose().trim(), BookingStatus.PENDING);
    }
    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(booking.getId(), booking.getInstructorName(), booking.getBookingDate(), booking.getQuantity(), booking.getPurpose(), booking.getStatus());
    }
}
