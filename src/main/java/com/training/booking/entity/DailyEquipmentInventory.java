package com.training.booking.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "daily_equipment_inventory")
public class DailyEquipmentInventory extends BaseEntity {
    @Column(name = "booking_date", nullable = false, unique = true)
    private LocalDate bookingDate;
    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity = 5;
    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity = 0;

    protected DailyEquipmentInventory() {}
    public DailyEquipmentInventory(LocalDate bookingDate) {
    	this.bookingDate = bookingDate; 
    	}
    public LocalDate getBookingDate() { 
    	return bookingDate; 
    	}
    public int getTotalQuantity() { 
    	return totalQuantity; 
    	}
    public int getReservedQuantity() {
    	return reservedQuantity; 
    	}
    public int getAvailableQuantity() { 
    	return totalQuantity - reservedQuantity; 
    	}
    public void reserve(int quantity) {
        if (quantity < 1 || quantity > getAvailableQuantity()) throw new IllegalArgumentException("Invalid reservation quantity for inventory");
        this.reservedQuantity += quantity;
    }
}
