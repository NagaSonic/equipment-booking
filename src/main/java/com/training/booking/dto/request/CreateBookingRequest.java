package com.training.booking.dto.request;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBookingRequest(
    @NotBlank(message = "instructorName is required") @Size(max = 100, message = "instructorName must be at most 100 characters") String instructorName,
    @NotNull(message = "bookingDate is required") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate bookingDate,
    @NotNull(message = "quantity is required") @Min(value = 1, message = "quantity must be at least 1") @Max(value = 5, message = "quantity must not exceed 5") Integer quantity,
    @NotBlank(message = "purpose is required") @Size(max = 500, message = "purpose must be at most 500 characters") String purpose
) {}
