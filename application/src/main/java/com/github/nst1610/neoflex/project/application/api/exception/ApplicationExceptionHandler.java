package com.github.nst1610.neoflex.project.application.api.exception;

import com.github.nst1610.neoflex.project.application.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(PrescoringException.class)
    public ResponseEntity<ErrorResponse> handleException(PrescoringException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
