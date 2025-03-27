package org.example.booktracker.utils;

public enum ConstantMessages {
    USER_SUCCESS_CREATED("User successfully created!"),
    BOOK_SUCCESS_CREATED("Book successfully created!"),
    BOOK_SUCCESS_ADDED("Book successfully added!");

    final String description;

    ConstantMessages(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
