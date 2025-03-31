package org.example.booktracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.book.GoogleBookDto;
import org.example.booktracker.domain.book.GoogleBooksResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GoogleBookService {
    // Logger
    final Logger logger = Logger.getLogger(GoogleBookService.class.getName());

    private final WebClient webClient;

    // Utils
    private final String API_KEY;
    private final String BASE_URL;
    private final Cache<String, List<GoogleBookDto>> googleBooksCache;

    public GoogleBookService(
            @Value("${spring.google-books.api_key}") String apiKey,
            @Value("${spring.google-books.base_url}") String baseUrl,
            WebClient.Builder webClientBuilder
    ) {
        this.API_KEY = apiKey;
        this.BASE_URL = baseUrl;
        this.webClient = webClientBuilder.build();
        googleBooksCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    public Mono<List<GoogleBookDto>> fetchBooks(String query) {
        var uri = UriComponentsBuilder.fromUriString(BASE_URL.concat("volumes"))
                .queryParam("q", query.trim())
                .queryParam("key", API_KEY)
                .build()
                .toUri();

        logger.info(() -> "Отправка запроса: " + uri);
        var fetchFromGoogleBooks = webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new IllegalStateException("Ошибка клиента: %s".formatted(response.toString()))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new IllegalStateException("Ошибка сервера: " + response.statusCode())))
                .bodyToMono(GoogleBooksResponse.class)
                .flatMap(response -> Mono.justOrEmpty(response.getItems())
                        .map(items -> items.stream()
                                .map(GoogleBooksResponse.Item::getVolumeInfo)
                                .collect(Collectors.toList())))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
                .doOnError(error -> logger.warning(
                        () -> "Ошибка запроса: %s".formatted(error.getMessage())));

        return Mono.justOrEmpty(googleBooksCache.getIfPresent(query))
                .switchIfEmpty(
                        fetchFromGoogleBooks
                                .doOnNext(result -> googleBooksCache.put(query, result))
                );
    }

}
