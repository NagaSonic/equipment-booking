package com.training.booking.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.training.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, UUID> {}
