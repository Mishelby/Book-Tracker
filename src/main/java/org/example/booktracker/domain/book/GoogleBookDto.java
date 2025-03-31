package org.example.booktracker.domain.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GoogleBookDto(
        String title,
        List<String> authors,
        String publishedDate,
        String description,
        Integer pageCount
) {
}
