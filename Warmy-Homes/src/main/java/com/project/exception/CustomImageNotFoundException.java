package com.project.exception;

public class CustomImageNotFoundException extends RuntimeException {
    public CustomImageNotFoundException(String message) {
        super(message);
    }

    public CustomImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
