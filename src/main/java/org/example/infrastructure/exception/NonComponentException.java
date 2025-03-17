package org.example.infrastructure.exception;

public class NonComponentException extends RuntimeException {
    public NonComponentException(String message) {
        super(message);
    }
}
