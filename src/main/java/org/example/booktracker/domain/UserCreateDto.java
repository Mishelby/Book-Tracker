package org.example.booktracker.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserCreateDto(
        String username,
        String email,
        String password
) {
}
