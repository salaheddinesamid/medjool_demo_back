package com.example.medjool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientExceptionController {


    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> clientNotFound(){
        return new ResponseEntity<>("Client not found... Please, select an appropriate client to move forward.", HttpStatus.NOT_FOUND);
    }

    // Handle client duplicate
    @ExceptionHandler(ClientAlreadyFoundException.class)
    public ResponseEntity<Object> clientAlreadyFound(ClientAlreadyFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.CONFLICT);
    }

    // Inactive clients are not allowed to make an order
    @ExceptionHandler(ClientNotActiveException.class)
    public ResponseEntity<Object> clientInActiveException(){
        return new ResponseEntity<>("Inactive clients are not allowed to purchase an order... Please, change the client",HttpStatus.CONFLICT);
    }
}
