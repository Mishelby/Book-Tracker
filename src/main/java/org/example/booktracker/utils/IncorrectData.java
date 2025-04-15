package org.example.booktracker.utils;

public class IncorrectData extends RuntimeException {
    public IncorrectData() {

    }
    public IncorrectData(String message) {
        super(message);
    }
}
