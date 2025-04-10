package org.example.booktracker.serviceClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.book.GoogleBookDto;
import org.example.booktracker.exception.PostValidationException;
import org.example.booktracker.exception.UnavailableServiceException;
import org.example.booktracker.utils.ConstantMessages;
import org.example.booktracker.utils.PostServiceAvailable;
import org.example.booktracker.utils.ResponseDto;
import org.example.booktracker.utils.SendPostDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class GoogleBookServiceClient {
    private static final String SERVICE_NANE = "Google Book Service";
    private final WebClient webClient;
    private final CacheManager cacheManager;

    public GoogleBookServiceClient(
            @Value("${spring.google-books.service.url}") String baseUrl,
            CacheManager cacheManager
    ) {
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
        this.cacheManager = cacheManager;
    }

    @CircuitBreaker(name = "googleBookService", fallbackMethod = "googleBookFallBack")
    @Cacheable(value = "googleBooks", key = "#query")
    public List<GoogleBookDto> findBooks(String query) {
        return webClient
                .post()
                .uri("/api/v1/google-books")
                .bodyValue(query)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new UnavailableServiceException(
                                        "Сервис %s в данный момент недоступен!".formatted(SERVICE_NANE)))
                                ))
                .bodyToMono(new ParameterizedTypeReference<List<GoogleBookDto>>() {
                })
                .block();
    }

    private List<GoogleBookDto> googleBookFallBack(
            String query, Throwable t
    ) {
        if (t instanceof UnavailableServiceException) {
            Cache cache = cacheManager.getCache("googleBooks");
            if (cache != null) {
                var googleBookDto = cache.get(query, GoogleBookDto.class);
                if (googleBookDto != null) {
                    return Collections.singletonList(googleBookDto);
                }
            }
        }

        return Collections.emptyList();
    }
}
