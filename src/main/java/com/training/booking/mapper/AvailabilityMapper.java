package com.training.booking.mapper;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import com.training.booking.dto.response.AvailabilityResponse;
import com.training.booking.entity.DailyEquipmentInventory;

@Component
public class AvailabilityMapper {
    public AvailabilityResponse toResponse(LocalDate date, int total, int reserved) {
        return new AvailabilityResponse(date, total, reserved, total - reserved);
    }
    public AvailabilityResponse toResponse(DailyEquipmentInventory inventory) {
        return toResponse(inventory.getBookingDate(), inventory.getTotalQuantity(), inventory.getReservedQuantity());
    }
}
