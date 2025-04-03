package org.example.booktracker.domain.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.booktracker.utils.WeatherServiceResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthorProfileDto(
        String name,
        Long countOfBooks,
        String status,
        WeatherServiceResponse weatherDTO
) {
}
