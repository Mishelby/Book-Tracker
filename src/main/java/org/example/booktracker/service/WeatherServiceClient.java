package org.example.booktracker.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.booktracker.utils.CityRequest;
import org.example.booktracker.utils.WeatherDto;
import org.example.booktracker.utils.WeatherServiceNotAvailable;
import org.example.booktracker.utils.WeatherServiceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class WeatherServiceClient {
    private final WebClient webClient;

    public WeatherServiceClient(
            @Value("${spring.weather.service.url}") String weatherServiceUrl
    ) {
        this.webClient = WebClient
                .builder()
                .baseUrl(weatherServiceUrl)
                .build();
    }

    @CircuitBreaker(name = "weatherService", fallbackMethod = "weatherFallback")
    public WeatherServiceResponse getWeather(CityRequest cityRequest) {
        return webClient.post()
                .uri("/api/v1/weather")
                .bodyValue(cityRequest)
                .retrieve()
                .bodyToMono(WeatherDto.class)
                .block();
    }

    private <T extends WeatherServiceResponse> WeatherServiceResponse weatherFallback() {
        return new WeatherServiceNotAvailable(
                0.0,
                "Сервис погоды временно недоступен!"
        );
    }
}
