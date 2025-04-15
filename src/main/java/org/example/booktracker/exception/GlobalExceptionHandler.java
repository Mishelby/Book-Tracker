package org.example.booktracker.exception;

import jakarta.persistence.EntityNotFoundException;
import org.example.booktracker.utils.IncorrectData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.ServiceUnavailableException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            value = {
                    UserAlreadyExists.class,
                    BookAlreadyExists.class,
                    EntityNotFoundException.class,
                    BookNotFoundException.class,
                    AuthorBookAlreadyExists.class,
                    FavoriteBookServiceException.class,
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

    @ExceptionHandler(
            value = {
                    PostServiceException.class,
                    PostValidationException.class,
                    IncorrectData.class
            }
    )
    public ResponseEntity<ErrorMessageResponse> handleException(
            RuntimeException exception
    ) {
        var errorMessageResponse = new ErrorMessageResponse(
                exception.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(
            value = {
                    ResponseStatusException.class,
                    ServiceUnavailableException.class
            }
    )
    public ResponseEntity<ErrorMessageResponse> handleException(
            ResponseStatusException exception
    ) {
        var errorMessageResponse = new ErrorMessageResponse(
                exception.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = UnavailableServiceException.class)
    public ResponseEntity<ErrorMessageResponse> handleException(
            UnavailableServiceException exception
    ) {
        var errorMessageResponse = new ErrorMessageResponse(
                exception.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
