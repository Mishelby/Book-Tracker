package org.example.booktracker.utils;

public enum ConstantMessages {
    USER_SUCCESS_CREATED("User successfully created!");

    final String description;

    ConstantMessages(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
