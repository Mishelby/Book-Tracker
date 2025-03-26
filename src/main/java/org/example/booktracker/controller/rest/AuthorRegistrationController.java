package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.Author.AuthorCreateDto;
import org.example.booktracker.service.AuthorRegistrationService;
import org.example.booktracker.utils.SuccessCreated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@Slf4j
@RestController
@RequestMapping("/api/v1/author-registration")
@RequiredArgsConstructor
public class AuthorRegistrationController {
    final Logger logger = Logger.getLogger(AuthorRegistrationController.class.getName());
    private final AuthorRegistrationService authorRegistrationService;

    @PostMapping
    public ResponseEntity<SuccessCreated> saveAuthor(
            @RequestBody AuthorCreateDto authorCreateDto
    ){
        logger.info(() -> "Post request for creating author = %s".formatted(authorCreateDto));
        return ResponseEntity.ok().body(authorRegistrationService.saveAuthor(authorCreateDto));
    }
}
