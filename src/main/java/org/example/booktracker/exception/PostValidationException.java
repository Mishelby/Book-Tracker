package org.example.booktracker.exception;

public class PostValidationException extends RuntimeException {
    public PostValidationException(String message) {
        super(message);
    }
}
