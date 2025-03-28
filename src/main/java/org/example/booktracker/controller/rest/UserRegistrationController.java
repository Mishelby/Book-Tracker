package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.user.UserCreateDto;
import org.example.booktracker.service.UserRegistrationService;
import org.example.booktracker.utils.SuccessCreated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class UserRegistrationController {
    private final Logger logger = Logger.getLogger(UserRegistrationController.class.getName());
    private final UserRegistrationService userRegistrationService;

    @PostMapping
    public ResponseEntity<SuccessCreated> registerUser(
            @RequestBody UserCreateDto user
    ) {
        logger.info(() -> "Post request for creating user = %s".formatted(user));
        return ResponseEntity.ok().body(userRegistrationService.saveUser(user));
    }
}
