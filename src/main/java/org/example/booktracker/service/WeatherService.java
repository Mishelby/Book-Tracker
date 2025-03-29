package org.example.booktracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.utils.CityRequest;
import org.example.booktracker.utils.WeatherDTO;
import org.example.booktracker.utils.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.logging.Logger;

@Slf4j
@Service
public class WeatherService {
    final Logger logger = Logger.getLogger(WeatherService.class.getName());
    private final String apiKey;
    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public WeatherService(
            @Value("${spring.weather.api_key}") String api,
            @Value("${spring.weather.base_url}") String url,
            RestTemplate restTemplate,
            ObjectMapper objectMapper
    ) {
        this.apiKey = api;
        this.baseUrl = url;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Cacheable(value = "weather", key = "#city")
    public WeatherDTO getWeather(
            CityRequest city
    ) {
        logger.info(() -> "Getting weather for city = %s".formatted(city.getCity()));
        var url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("q", city.getCity())
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .build()
                .toUriString();


        var json = restTemplate.getForObject(url, String.class);
        logger.info(() -> "Got forObject = %s".formatted(json));

        if (json == null || json.isEmpty()) {
            throw new IllegalStateException("Got empty response");
        }

        try {
            var weatherResponse = objectMapper.readValue(json, WeatherResponse.class);
            return new WeatherDTO(
                    weatherResponse.getMain().getTemp(),
                    weatherResponse.getWeather().get(0).getDescription()
            );
        } catch (JsonProcessingException e) {
            logger.warning(() -> "Got error response = %s".formatted(e.getMessage()));
            return new WeatherDTO();
        }
    }
}
