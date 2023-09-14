package com.github.nst1610.neoflex.project.deal.api.exception;

import com.github.nst1610.neoflex.project.deal.util.ErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DealExceptionHandler {
    @ExceptionHandler(value = ApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ApplicationNotFoundException ex){
        ErrorResponse response = new ErrorResponse("Заявки с таким id не существует",
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<ErrorResponse> handleException(FeignException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ApplicationStatusException.class)
    public ResponseEntity<ErrorResponse> handleException(ApplicationStatusException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = VerifySesCodeException.class)
    public ResponseEntity<ErrorResponse> handleException(VerifySesCodeException ex){
        ErrorResponse response = new ErrorResponse("Некорректный SES-code.", System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
