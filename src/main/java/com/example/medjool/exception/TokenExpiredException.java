package com.example.medjool.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super("JWT Token expired");
    }
}
