package com.github.nst1610.neoflex.project.dossier.exception;

import com.github.nst1610.neoflex.project.dossier.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DossierExceptionHandler {
    @ExceptionHandler(value = CreatingFileException.class)
    public ResponseEntity<ErrorResponse> handleException(CreatingFileException ex){
        ErrorResponse response = new ErrorResponse("Ошибка при создании файла.",
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SendEmailException.class)
    public ResponseEntity<ErrorResponse> handleException(SendEmailException ex){
        ErrorResponse response = new ErrorResponse("Ошибка при отправке сообщения клиенту.",
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
