package org.example.booktracker.domain.Author;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthorCreateDto(
        String username,
        String email,
        String password,
        String name
) {
}
