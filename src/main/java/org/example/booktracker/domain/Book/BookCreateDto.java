package org.example.booktracker.domain.Book;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookCreateDto(
        String name,
        String description
) {
}
