package org.example.booktracker.exception;

public class PostServiceException extends RuntimeException {
    public PostServiceException(String message) {
        super(message);
    }
}
