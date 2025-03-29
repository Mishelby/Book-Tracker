package org.example.booktracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.UUID;

@Configuration
public class UUIDConfig {
    @Bean
    @Scope("prototype")
    public UUID uuidGenerator() {
        return UUID.randomUUID();
    }
}
