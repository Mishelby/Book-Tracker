package org.example.booktracker.exception;

public class BookAlreadyExists extends RuntimeException {
    public BookAlreadyExists(String message) {
        super(message);
    }

    public BookAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public BookAlreadyExists(Throwable cause) {
        super(cause);
    }

    public BookAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
