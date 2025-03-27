package org.example.booktracker.exception;

public class AuthorBookAlreadyExists extends RuntimeException {
    public AuthorBookAlreadyExists(String message) {
        super(message);
    }

    public AuthorBookAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorBookAlreadyExists(Throwable cause) {
        super(cause);
    }

    public AuthorBookAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
