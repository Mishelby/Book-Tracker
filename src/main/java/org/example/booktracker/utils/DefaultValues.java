package org.example.booktracker.utils;
;
import lombok.Getter;

@Getter
public class DefaultValues {
    private static final Double DEFAULT_RATING = 0.0;
    private static final Double BEST_RATING = 3.0;

    public static Double getDefaultRating() {
        return DEFAULT_RATING;
    }

    public static Double getBestRating() {
        return BEST_RATING;
    }
}
