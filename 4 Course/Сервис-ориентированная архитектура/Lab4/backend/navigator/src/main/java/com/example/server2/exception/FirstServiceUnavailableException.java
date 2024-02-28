package com.example.server2.exception;

public class FirstServiceUnavailableException extends RuntimeException {
    public FirstServiceUnavailableException(String message) {
        super(message);
    }
}
