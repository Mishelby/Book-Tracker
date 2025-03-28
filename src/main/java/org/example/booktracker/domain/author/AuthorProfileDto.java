package org.example.booktracker.domain.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.booktracker.utils.WeatherDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthorProfileDto(
        String name,
        Long countOfBooks,
        String status,
        WeatherDTO weatherDTO
) {
}
