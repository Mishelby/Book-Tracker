package org.example.booktracker.serviceClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.comment.LastNCommentsDto;
import org.example.booktracker.exception.UnavailableServiceException;
import org.example.booktracker.utils.CommentDto;
import org.example.booktracker.utils.DefaultCommentDto;
import org.example.booktracker.utils.IncorrectData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.naming.ServiceUnavailableException;
import java.time.Duration;


@Slf4j
@Service
public class CommentClientService {
    private static final String SERVICE_NANE = "Comment Client Service";
    private final WebClient webClient;

    public CommentClientService(
            @Value("${spring.comment.service.url}") String commentServiceUrl
    ) {
        this.webClient = WebClient
                .builder()
                .baseUrl(commentServiceUrl)
                .build();
    }

    @Retryable(noRetryFor = {EntityNotFoundException.class, IncorrectData.class})
    @CircuitBreaker(name = SERVICE_NANE, fallbackMethod = "getLastNFallBack")
    @Cacheable(value = "lastNComments", key = "#userId + '_' + #count")
    public Mono<LastNCommentsDto> getNLastComments(
            Long userId,
            Long count
    ) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/comment/{userId}")
                        .queryParam("count", count)
                        .build(userId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.createException()
                                .flatMap(ex -> Mono.error(mapError(userId, ex))))
                .bodyToMono(LastNCommentsDto.class)
                .timeout(Duration.ofSeconds(5))
                .doOnError(e -> log.warn("Error get {} last commentaries: {}", userId, e.getMessage()));
    }

    @CacheEvict(value = "lastNComments", allEntries = true)
    public CommentDto sendComment(DefaultCommentDto defaultCommentDto) {
        return webClient.post()
                .uri("/api/v1/comment")
                .bodyValue(defaultCommentDto)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new UnavailableServiceException(
                                        "Сервис %s в данный момент недоступен!".formatted(SERVICE_NANE)))
                                ))
                .bodyToMono(CommentDto.class)
                .block();
    }

    public Mono<LastNCommentsDto> getLastNFallBack(
            Long userId,
            Long count,
            Throwable t
    ) {
        return Mono.error(new ServiceUnavailableException(
                "Не удалось получить последние комментарии. Сервис недоступен. Попробуйте позже!"
        ));
    }

    private Throwable mapError(Long userId, WebClientResponseException ex) {
        HttpStatusCode status = ex.getStatusCode();

        if (status.equals(HttpStatus.NOT_FOUND)) {
            return new EntityNotFoundException(ex.getMessage());
        } else if (status.is5xxServerError()) {
            return new ServiceUnavailableException(ex.getMessage());
        } else {
            return new IncorrectData("Некорректные данные для пользователя %s! %s"
                    .formatted(userId, ex.getMessage()));
        }
    }
}
