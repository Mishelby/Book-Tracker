package org.example.booktracker.serviceClient;

import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.comment.LastNCommentsDto;
import org.example.booktracker.exception.UnavailableServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


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

    @Cacheable(value = "lastNComments", key = "#userId + '_' + #count")
    public LastNCommentsDto getNLastComments(
            Long userId,
            Long count
    ) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/comment/{userId}")
                        .queryParam("count", count)
                        .build(userId))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(error -> Mono.error(new UnavailableServiceException(
                                        "Сервис %s в данный момент недоступен!".formatted(SERVICE_NANE)))
                                ))
                .bodyToMono(LastNCommentsDto.class)
                .block();
    }
}
