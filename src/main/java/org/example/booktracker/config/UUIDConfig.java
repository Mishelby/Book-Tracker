package org.example.booktracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class UUIDConfig {
    @Bean
    public UUID uuidGenerator() {
        return UUID.randomUUID();
    }
}
