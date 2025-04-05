package com.example.medjool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderExceptionController {


    @ExceptionHandler(OrderCannotBeCanceledException.class)
    public ResponseEntity<Object> handleOrderCannotBeCanceledException(OrderCannotBeCanceledException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
