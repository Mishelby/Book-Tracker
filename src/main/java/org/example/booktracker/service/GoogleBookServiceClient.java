package org.example.booktracker.service;

import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.book.GoogleBookDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class GoogleBookServiceClient {
    private final WebClient webClient;

    public GoogleBookServiceClient(
            @Value("${spring.google-books.service.url}") String baseUrl
    ) {
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    public List<GoogleBookDto> findBooks(String query) {
        return webClient
                .post()
                .uri("/api/v1/google-books")
                .bodyValue(query)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GoogleBookDto>>() {})
                .block();
    }
}
