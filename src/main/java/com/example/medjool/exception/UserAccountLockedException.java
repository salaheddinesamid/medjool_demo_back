package com.example.medjool.exception;

public class UserAccountLockedException extends RuntimeException {
  public UserAccountLockedException(String message) {
    super(message);
  }
}
