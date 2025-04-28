package com.example.medjool.exception;

public class ClientAlreadyFoundException extends RuntimeException {

    public ClientAlreadyFoundException(String message) {
        super("Client already exists");
    }

    public ClientAlreadyFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientAlreadyFoundException(Throwable cause) {
        super(cause);
    }

}
