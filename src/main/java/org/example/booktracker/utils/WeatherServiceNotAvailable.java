package org.example.booktracker.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class WeatherServiceNotAvailable implements WeatherServiceResponse {
    Double temperature;
    String description;
}
