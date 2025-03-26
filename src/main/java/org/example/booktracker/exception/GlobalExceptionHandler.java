package org.example.booktracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            value = {
                    UserAlreadyExists.class
            }
    )
    public ResponseEntity<ErrorMessageResponse> handleException(
            UserAlreadyExists exception
    ) {
        var errorMessageResponse = new ErrorMessageResponse(
                exception.getMessage(),
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.BAD_REQUEST) ;
    }
}
