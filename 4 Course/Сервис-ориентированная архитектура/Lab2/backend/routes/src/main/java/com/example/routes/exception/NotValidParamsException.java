package com.example.routes.exception;

public class NotValidParamsException extends RuntimeException {
    public NotValidParamsException(String message) {
        super(message);
    }
}
