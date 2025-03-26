package org.example.booktracker.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SuccessCreated(
        String userInfo,
        String message,
        String created_at
) {
}
