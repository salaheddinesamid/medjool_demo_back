package com.example.medjool.exception;

public class OrderCannotBeCanceledException extends RuntimeException {

    public OrderCannotBeCanceledException(String message) {
        super("Order cannot be canceled at the moment...");
    }

    public OrderCannotBeCanceledException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderCannotBeCanceledException(Throwable cause) {
        super(cause);
    }
}
