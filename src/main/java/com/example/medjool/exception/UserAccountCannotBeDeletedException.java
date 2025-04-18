package com.example.medjool.exception;

public class UserAccountCannotBeDeletedException extends RuntimeException {
  public UserAccountCannotBeDeletedException(String message) {
    super(message);
  }
}
