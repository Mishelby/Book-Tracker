package org.example.booktracker.utils;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class WeatherDto implements WeatherServiceResponse {
    Double temperature;
    String description;
}