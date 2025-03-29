package org.example.booktracker.domain.authorBook;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthorBookDto(
        Long countOfBooks,
        String name,
        String article
) {
}
