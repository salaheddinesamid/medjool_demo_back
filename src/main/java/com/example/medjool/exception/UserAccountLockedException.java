package com.example.medjool.exception;

public class UserAccountLockedException extends RuntimeException {

    public UserAccountLockedException(String message) {
        super("User account is locked");
    }

    public UserAccountLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAccountLockedException(Throwable cause) {
        super(cause);
    }
}
