package org.example.booktracker.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WeatherServiceNotAvailable implements WeatherServiceResponse {
    double temperature;
    String description;
}
