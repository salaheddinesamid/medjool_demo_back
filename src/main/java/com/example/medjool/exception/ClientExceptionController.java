package com.example.medjool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientExceptionController {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> clientNotFound(){
        return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
    }


}
