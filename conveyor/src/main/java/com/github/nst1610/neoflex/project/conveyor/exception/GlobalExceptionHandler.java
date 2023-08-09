package com.github.nst1610.neoflex.project.conveyor.exception;

import com.github.nst1610.neoflex.project.conveyor.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ScoringException.class, DataException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
