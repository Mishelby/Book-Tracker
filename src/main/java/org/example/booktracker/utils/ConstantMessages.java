package org.example.booktracker.utils;

import lombok.Getter;

@Getter
public enum ConstantMessages {
    USER_SUCCESS_CREATED("User successfully created!"),
    BOOK_SUCCESS_CREATED("Book successfully created!"),
    BOOK_SUCCESS_ADDED("Book successfully added!"),
    START_MESSAGE(formatMessage("Добро пожаловать в книжный дом! %s"));

    final String description;
    static final String MESSAGE = "Мы занимаемся распространением знаний!";

    ConstantMessages(String description) {
        this.description = description;
    }

    private static String formatMessage(String template) {
        return String.format(template, MESSAGE);
    }

}
