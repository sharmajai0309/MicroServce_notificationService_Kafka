package com.notification.api.exception;

import lombok.Getter;

public class ResourceNotFoundException extends RuntimeException implements AbstractException {


    Integer StatusCode;

    public ResourceNotFoundException(String message) {
        super(message);
    }


    public ResourceNotFoundException(String message, Integer StatusCode) {
        super(message);
        this.StatusCode = StatusCode;
    }

    @Override
    public int getStatusCode() {
        return StatusCode;
    }

    @Override
    public String getErrorMessage() {
        return getMessage();
    }
}
