package com.revworkforce.performance_service.exception;

public class ReviewNotReadyException extends RuntimeException {

    public ReviewNotReadyException(String message) {
        super(message);
    }
}