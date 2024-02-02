package com.exception;

public class FirstServiceUnavailableException extends RuntimeException {
    public FirstServiceUnavailableException(String message) {
        super(message);
    }
}
