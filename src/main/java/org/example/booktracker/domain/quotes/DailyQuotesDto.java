package org.example.booktracker.domain.quotes;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DailyQuotesDto(
        String title,
        String author,
        String description
) {
}
