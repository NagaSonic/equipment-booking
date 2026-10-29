package com.training.booking.service.impl;

import java.time.Clock;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.training.booking.dto.request.CreateBookingRequest;
import com.training.booking.dto.response.BookingResponse;
import com.training.booking.entity.Booking;
import com.training.booking.entity.DailyEquipmentInventory;
import com.training.booking.exception.BookingNotFoundException;
import com.training.booking.exception.InsufficientAvailabilityException;
import com.training.booking.exception.InvalidBookingDateException;

import org.springframework.stereotype.Service;

import com.training.booking.repository.BookingRepository;
import com.training.booking.repository.DailyEquipmentInventoryRepository;
import com.training.booking.service.BookingService;
import com.training.booking.mapper.BookingMapper;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final DailyEquipmentInventoryRepository inventoryRepository;
    private final BookingMapper bookingMapper;
	private Clock clock;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            DailyEquipmentInventoryRepository inventoryRepository,
            BookingMapper bookingMapper,  Clock clock) {
        this.bookingRepository = bookingRepository;
        this.inventoryRepository = inventoryRepository;
        this.bookingMapper = bookingMapper;
        this.clock = clock;;
    }
    
    @Override
    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request) {
        LocalDate today = LocalDate.now(clock);
        if (request.bookingDate().isBefore(today)) throw new InvalidBookingDateException();

        inventoryRepository.createIfAbsent(request.bookingDate());
        DailyEquipmentInventory inventory = inventoryRepository.findByBookingDateForUpdate(request.bookingDate())
            .orElseThrow(() -> new IllegalStateException("Inventory row could not be initialized."));

        if (request.quantity() > inventory.getAvailableQuantity()) {
            throw new InsufficientAvailabilityException(request.bookingDate(), request.quantity(), inventory.getAvailableQuantity());
        }
        inventory.reserve(request.quantity());
        Booking booking = bookingMapper.toEntity(request);
        Booking saved = bookingRepository.save(booking);
        return bookingMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBooking(UUID id) {
        return bookingMapper.toResponse(bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException(id)));
    }

    }


