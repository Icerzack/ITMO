package com.example.server2.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Сущность не найдена");
    }
}
