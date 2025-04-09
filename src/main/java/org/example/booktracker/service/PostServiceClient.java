package org.example.booktracker.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.booktracker.exception.PostServiceException;
import org.example.booktracker.exception.PostValidationException;
import org.example.booktracker.utils.PostServiceAvailable;
import org.example.booktracker.utils.ResponseDto;
import org.example.booktracker.utils.ResponseForDto;
import org.example.booktracker.utils.SendPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PostServiceClient {
    private final WebClient webClient;

    @Autowired
    public PostServiceClient(
            @Value("${spring.post.service.url}") String postServiceUrl
    ) {
        this.webClient = WebClient
                .builder()
                .baseUrl(postServiceUrl)
                .build();
    }

    @CircuitBreaker(name = "postService", fallbackMethod = "postFallBack")
    public ResponseDto sendPost(SendPostDto sendPostDto) {
        return webClient.post()
                .uri("/api/v1/post")
                .bodyValue(sendPostDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(
                                error -> Mono.error(new PostValidationException(error))
                        )
                ).onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(String.class).flatMap(
                                error -> Mono.error(new PostServiceException(error))
                        ))
                .bodyToMono(ResponseDto.class)
                .block();
    }


    private <T extends ResponseDto> ResponseDto postFallBack(
            SendPostDto sendPostDto, Throwable t
    ) {
        if (t instanceof PostValidationException) {
            return new PostServiceAvailable(
                    "Ошибка валидации: " + t.getMessage(),
                    "Проверьте данные",
                    LocalDateTime.now()
            );
        }

        return new PostServiceAvailable(
                "Сервис постов в данный момент недоступен! Повторите попытку позже",
                "Ведутся технические работы",
                LocalDateTime.now()
        );
    }
}


