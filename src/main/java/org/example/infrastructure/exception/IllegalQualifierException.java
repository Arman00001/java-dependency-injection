package org.example.infrastructure.exception;

public class IllegalQualifierException extends RuntimeException {
    public IllegalQualifierException(String message) {
        super(message);
    }
}
