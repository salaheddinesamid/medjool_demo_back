package com.example.medjool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionController {

    @ExceptionHandler(UserAccountLockedException.class)
    public ResponseEntity<Object> userAccountLockedException(UserAccountLockedException e) {
        return new ResponseEntity<>("User account is locked. Please contact support.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>("User not found. Please contact support.", HttpStatus.NOT_FOUND);
    }
}
