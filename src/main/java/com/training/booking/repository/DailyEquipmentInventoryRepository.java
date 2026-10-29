package com.training.booking.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import com.training.booking.entity.DailyEquipmentInventory;

public interface DailyEquipmentInventoryRepository extends JpaRepository<DailyEquipmentInventory, UUID> {
    Optional<DailyEquipmentInventory> findByBookingDate(LocalDate bookingDate);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO daily_equipment_inventory (id, created_at, updated_at, created_by, updated_by, booking_date, total_quantity, reserved_quantity) VALUES (gen_random_uuid(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SYSTEM', 'SYSTEM', :bookingDate, 5, 0) ON CONFLICT (booking_date) DO NOTHING", nativeQuery = true)
    void createIfAbsent(@Param("bookingDate") LocalDate bookingDate);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from DailyEquipmentInventory i where i.bookingDate = :bookingDate")
    Optional<DailyEquipmentInventory> findByBookingDateForUpdate(@Param("bookingDate") LocalDate bookingDate);
}
