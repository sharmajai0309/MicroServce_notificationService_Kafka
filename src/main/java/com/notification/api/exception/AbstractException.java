package com.notification.api.exception;

public interface AbstractException {

    Integer getStatusCode();
    String getErrorMessage();
}
