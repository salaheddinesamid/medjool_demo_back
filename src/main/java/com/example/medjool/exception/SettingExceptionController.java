package com.example.medjool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SettingExceptionController {
    @ExceptionHandler(SettingNotFoundException.class)
    public ResponseEntity<Object> handleSettingNotFoundException(SettingNotFoundException e) {
        return new ResponseEntity<>("Setting not found", HttpStatus.NOT_FOUND);
    }
}
