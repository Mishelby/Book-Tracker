package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.city.CityCreateDto;
import org.example.booktracker.domain.city.CityDto;
import org.example.booktracker.service.CityService;
import org.example.booktracker.utils.SuccessCreated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {
    final Logger logger = Logger.getLogger(CityController.class.getName());
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<SuccessCreated> createCity(
            @RequestBody CityCreateDto cityCreateDto
    ) {
        logger.info(() -> "Post request for creating city = %s".formatted(cityCreateDto));
        return ResponseEntity.ok().body(cityService.createCity(cityCreateDto.name()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(
            @PathVariable("id") Long id
    ) {
        logger.info(() -> "Get request for getting city by id = %s".formatted(id));
        return null;
    }
}
