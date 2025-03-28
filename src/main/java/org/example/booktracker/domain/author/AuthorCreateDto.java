package org.example.booktracker.domain.author;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthorCreateDto(
        String username,
        String email,
        String password,
        String name,
        String cityName
) {
}
