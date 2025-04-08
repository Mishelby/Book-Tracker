package org.example.booktracker.domain.authorBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public record AuthorBookDto(
        Long countOfBooks,
        String name,
        String article
) {
}
