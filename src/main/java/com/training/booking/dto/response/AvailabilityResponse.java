package com.training.booking.dto.response;

import java.time.LocalDate;

public record AvailabilityResponse(LocalDate date, int totalQuantity, int reservedQuantity, int availableQuantity) {}
