package com.github.nst1610.neoflex.project.gateway.api.exception;

import com.github.nst1610.neoflex.project.gateway.util.ErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GatewayExceptionHandler {
    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<ErrorResponse> handleException(FeignException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
