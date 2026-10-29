package com.training.booking.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
    @Column(name = "instructor_name", nullable = false, length = 100)
    private String instructorName;
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false, length = 500)
    private String purpose;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status;

    protected Booking() {}
    public Booking(String instructorName, LocalDate bookingDate, Integer quantity, String purpose, BookingStatus status) {
        this.instructorName = instructorName;
        this.bookingDate = bookingDate;
        this.quantity = quantity;
        this.purpose = purpose;
        this.status = status;
    }
    public String getInstructorName() { 
    	return instructorName; 
    	}
    public LocalDate getBookingDate() {
    	return bookingDate;
    	}
    public Integer getQuantity() { 
    	return quantity; 
    	}
    public String getPurpose() {
    	return purpose; 
    	}
    public BookingStatus getStatus() { 
    	return status;
    	}
}
