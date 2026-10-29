package com.training.booking.controller;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.training.booking.dto.response.AvailabilityResponse;
import com.training.booking.service.EquipmentAvailabilityService;

@RestController
@RequestMapping("/api/v1/equipment")
public class EquipmentController {
    private final EquipmentAvailabilityService availabilityService;
    public EquipmentController(EquipmentAvailabilityService availabilityService) {
    	this.availabilityService = availabilityService;
    	}

    @GetMapping("/availability")
    public ResponseEntity<AvailabilityResponse> availability(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(availabilityService.getAvailability(date));
    }
}
