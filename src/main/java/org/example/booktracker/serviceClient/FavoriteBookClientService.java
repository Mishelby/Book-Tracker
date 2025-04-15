package org.example.booktracker.serviceClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.exception.FavoriteBookServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class FavoriteBookClientService {
    private static final String SERVICE_NAME = "FAVORITE_BOOK_SERVICE";
    private final WebClient webClient;

    @Autowired
    public FavoriteBookClientService(
            @Value("${spring.favorite-book.service.url}")
            String favoriteBookServiceUrl
    ) {
        this.webClient = WebClient
                .builder()
                .baseUrl(favoriteBookServiceUrl)
                .build();
    }

    @Retry(name = "favoriteBookFallBack")
    @CircuitBreaker(name = "favoriteBooks", fallbackMethod = "favoriteBookFallBack")
    public List<Long> userFavoriteBooks(Long userId) {
        try {
            return webClient.get()
                    .uri("/api/v1/favorite-books/{id}", userId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(error -> Mono.error(new FavoriteBookServiceException(
                                            "Сервис %s в данный момент недоступен!".formatted(SERVICE_NAME)))))
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            clientResponse.bodyToMono(String.class)
                                    .flatMap(error -> Mono.error(new EntityNotFoundException(
                                            "Пользователь с id %s не найден!".formatted(userId)))))
                    .bodyToMono(new ParameterizedTypeReference<List<Long>>() {})
                    .block();
        } catch (WebClientRequestException e) {
            throw new FavoriteBookServiceException("Ошибка подключения к сервису любимых книг: %s".formatted(e.getMessage()));
        }
    }

    public List<Long> favoriteBookFallBack(
            Long userId,
            Throwable t
    ) {
        log.warn("Falling back для userFavoriteBooks({}): {}", userId, t.toString());
        if (t instanceof FavoriteBookServiceException) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    t.getMessage());
        }

        if (t instanceof EntityNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    t.getMessage());
        }

        log.error("Неизвестная ошибка userFavoriteBooks({}): {}", userId, t.getMessage());
        return Collections.emptyList();
    }
}
