package com.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Сущность не найдена");
    }
}
