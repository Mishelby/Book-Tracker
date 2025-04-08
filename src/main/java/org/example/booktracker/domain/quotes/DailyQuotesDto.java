package org.example.booktracker.domain.quotes;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DailyQuotesDto(
        String title,
        String author,
        String description
) {
}
