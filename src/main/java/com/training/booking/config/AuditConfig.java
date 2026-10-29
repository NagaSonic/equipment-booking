package com.training.booking.config;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

// requirmnt is not in phase 1, so just mentioned the System as a every update

@Configuration
public class AuditConfig {
    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.of("SYSTEM");
    }
}
