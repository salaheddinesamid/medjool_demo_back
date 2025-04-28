package com.example.medjool.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super("User already exists");
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
