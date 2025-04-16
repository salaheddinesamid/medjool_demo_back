package com.example.medjool.exception;

public class OrderCannotBeChangedException extends RuntimeException {
  public OrderCannotBeChangedException(String message) {
    super(message);
  }
}
