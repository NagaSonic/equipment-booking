package com.training.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EquipmentBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(EquipmentBookingApplication.class, args);
    }
}
