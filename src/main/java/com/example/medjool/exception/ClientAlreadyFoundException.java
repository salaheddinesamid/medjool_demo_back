package com.example.medjool.exception;

public class ClientAlreadyFoundException extends RuntimeException {
  public ClientAlreadyFoundException(String message) {
    super(message);
  }
}
