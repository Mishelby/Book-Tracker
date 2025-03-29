package org.example.booktracker.domain.book;

import java.util.Arrays;

public enum BookGenre {
    FANTASY("Фэнтези"),
    DETECTIVE("Детектив"),
    CLASSIC("Классическая");

    String name;

    BookGenre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BookGenre getBookGenre(String bookGenre) {
        return Arrays.stream(BookGenre.values())
                .filter(genre -> genre.name.equals(bookGenre))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such genre: %s ".formatted(bookGenre)));
    }
}
