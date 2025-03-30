package org.example.booktracker.utils;
;
import lombok.Getter;

@Getter
public class DefaultValues {
    private static final Double DEFAULT_RATING = 0.0;

    public static Double getDefaultRating() {
        return DEFAULT_RATING;
    }
}
