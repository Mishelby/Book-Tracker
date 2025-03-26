package org.example.booktracker.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktracker.domain.UserProfileInfoDto;
import org.example.booktracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileInfoDto> getUser(
            @PathVariable Long id
    ) {
        logger.info(() -> "Get request for find user by id = %s".formatted(id));
        return ResponseEntity.ok().body(userService.getUserById(id));
    }
}
