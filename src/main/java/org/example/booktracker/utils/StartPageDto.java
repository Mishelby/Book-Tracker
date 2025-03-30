package org.example.booktracker.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.booktracker.domain.author.BestAuthorDto;
import org.example.booktracker.domain.quotes.DailyQuotesDto;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StartPageDto(
       String startMessage,
       List<BestAuthorDto> bestAuthors,
       Long countOfUsers,
       Long countOfAuthors,
       Long countOfBooks,
       String dailyQuote,
       List<DailyQuotesDto> dailyQuotes
) {
}
