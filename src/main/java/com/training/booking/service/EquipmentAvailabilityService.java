package com.training.booking.service;

import java.time.LocalDate;
import com.training.booking.dto.response.AvailabilityResponse;

public interface EquipmentAvailabilityService {
    AvailabilityResponse getAvailability(LocalDate date);
}
