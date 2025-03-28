package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.service.WeatherService;
import org.example.booktracker.utils.CityRequest;
import org.example.booktracker.utils.WeatherDTO;
import org.example.booktracker.utils.WeatherResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
public class WeatherController {
    final Logger logger = Logger.getLogger(WeatherController.class.getName());
    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherDTO> getWeather(
            @RequestBody CityRequest city
    ) {
        logger.info(() -> "Get request weather for city = %s ".formatted(city));
        return ResponseEntity.ok().body(weatherService.getWeather(city));
    }

}
