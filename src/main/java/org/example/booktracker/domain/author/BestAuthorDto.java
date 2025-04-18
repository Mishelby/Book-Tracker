package org.example.booktracker.domain.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BestAuthorDto(
        Double rating,
        String name,
        List<String> bestBooks
) {
}
