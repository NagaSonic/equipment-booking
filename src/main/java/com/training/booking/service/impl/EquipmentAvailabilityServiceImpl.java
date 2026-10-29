package com.training.booking.service.impl;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.training.booking.dto.response.AvailabilityResponse;
import com.training.booking.mapper.AvailabilityMapper;
import com.training.booking.repository.DailyEquipmentInventoryRepository;
import com.training.booking.service.EquipmentAvailabilityService;

@Service
public class EquipmentAvailabilityServiceImpl implements EquipmentAvailabilityService {
    private final DailyEquipmentInventoryRepository repository;
    private final AvailabilityMapper mapper;
    public EquipmentAvailabilityServiceImpl(DailyEquipmentInventoryRepository repository, AvailabilityMapper mapper) { this.repository = repository; this.mapper = mapper; }

    @Override
    @Transactional(readOnly = true)
    public AvailabilityResponse getAvailability(LocalDate date) {
        return repository.findByBookingDate(date)
            .map(mapper::toResponse)
            .orElseGet(() -> mapper.toResponse(date, 5, 0));
    }
}



