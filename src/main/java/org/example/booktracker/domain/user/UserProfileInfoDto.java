package org.example.booktracker.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserProfileInfoDto(
        String userName,
        String status,
        String email,
        Integer countOfBooks
) {
}
