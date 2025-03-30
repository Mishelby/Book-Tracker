package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.service.StartPageService;
import org.example.booktracker.utils.StartPageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/start")
@RequiredArgsConstructor
public class StartPageController {
    final Logger logger = Logger.getLogger(StartPageController.class.getName());
    private final StartPageService startPageService;

    @GetMapping
    public ResponseEntity<StartPageDto> getStartPage() {
        logger.info(() -> "Get request for getting information about start page");
        return ResponseEntity.ok().body(startPageService.getStartPage());
    }
}
