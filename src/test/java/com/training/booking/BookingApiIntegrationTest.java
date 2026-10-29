package com.training.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.booking.repository.BookingRepository;
import com.training.booking.repository.DailyEquipmentInventoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class BookingApiIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    @DynamicPropertySource static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired BookingRepository bookingRepository;
    @Autowired DailyEquipmentInventoryRepository inventoryRepository;

    private String request(LocalDate date, int quantity, String name) throws Exception {
        return objectMapper.writeValueAsString(java.util.Map.of("instructorName", name, "bookingDate", date.toString(), "quantity", quantity, "purpose", "Training"));
    }

    @Test void createBookingReservesQuantityAndReturns201() throws Exception {
        LocalDate date = LocalDate.now().plusDays(5);
        String response = mvc.perform(post("/api/v1/bookings").contentType(MediaType.APPLICATION_JSON).content(request(date, 3, "Ravi")))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        JsonNode json = objectMapper.readTree(response);
        assertThat(json.get("status").asText()).isEqualTo("PENDING");
        mvc.perform(get("/api/v1/equipment/availability").param("date", date.toString()))
            .andExpect(status().isOk());
        assertThat(inventoryRepository.findByBookingDate(date).orElseThrow().getAvailableQuantity()).isEqualTo(2);
    }

    @Test void invalidQuantityReturns400() throws Exception {
        mvc.perform(post("/api/v1/bookings").contentType(MediaType.APPLICATION_JSON).content(request(LocalDate.now().plusDays(1), 6, "Ravi")))
            .andExpect(status().isBadRequest());
    }

    @Test void simultaneousRequestsCannotOverbook() throws Exception {
        LocalDate date = LocalDate.now().plusDays(10);
        CountDownLatch ready = new CountDownLatch(2);
        CountDownLatch start = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            Future<Integer> first = pool.submit(() -> { ready.countDown(); start.await(); return postStatus(date, "A"); });
            Future<Integer> second = pool.submit(() -> { ready.countDown(); start.await(); return postStatus(date, "B"); });
            ready.await(); start.countDown();
            int a = first.get(); int b = second.get();
            assertThat(java.util.List.of(a, b)).contains(201, 409);
            assertThat(bookingRepository.findAll().stream().filter(x -> x.getBookingDate().equals(date)).mapToInt(x -> x.getQuantity()).sum()).isEqualTo(3);
            assertThat(inventoryRepository.findByBookingDate(date).orElseThrow().getReservedQuantity()).isEqualTo(3);
        } finally { pool.shutdownNow(); }
    }
    private int postStatus(LocalDate date, String name) throws Exception {
        return mvc.perform(post("/api/v1/bookings").contentType(MediaType.APPLICATION_JSON).content(request(date, 3, name))).andReturn().getResponse().getStatus();
    }
}
