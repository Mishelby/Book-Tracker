package org.example.booktracker.domain.book;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookCreateDto(
        String name,
        String description,
        String genre
) {
}
