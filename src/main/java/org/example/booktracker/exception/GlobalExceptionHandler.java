package org.example.booktracker.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            value = {
                    UserAlreadyExists.class,
                    BookAlreadyExists.class,
                    EntityNotFoundException.class,
                    BookNotFoundException.class,
                    AuthorBookAlreadyExists.class
            }
    )
    public ResponseEntity<ErrorMessageResponse> handleException(
            Exception exception
    ) {
        var errorMessageResponse = new ErrorMessageResponse(
                exception.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.BAD_REQUEST);
    }

}
