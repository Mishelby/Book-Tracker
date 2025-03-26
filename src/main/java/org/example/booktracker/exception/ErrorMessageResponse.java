package org.example.booktracker.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorMessageResponse(
        String message,
        String created_at
) {
}
