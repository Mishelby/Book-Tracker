package org.example.booktracker.domain.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.booktracker.utils.WeatherServiceResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public record AuthorProfileDto(
        String name,
        Long countOfBooks,
        String status,
        WeatherServiceResponse weatherDTO
){
}
