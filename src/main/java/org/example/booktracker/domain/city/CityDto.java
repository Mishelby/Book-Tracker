package org.example.booktracker.domain.city;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CityDto(
        String name
) {
}
