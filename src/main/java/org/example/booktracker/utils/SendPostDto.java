package org.example.booktracker.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SendPostDto(
        Long userId,
        String postName,
        String description
) {
}
