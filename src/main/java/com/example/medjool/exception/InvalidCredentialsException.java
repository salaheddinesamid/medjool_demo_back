package com.example.medjool.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super("Invalid credentials,please try again");
    }
}
