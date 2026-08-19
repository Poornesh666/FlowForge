package com.flowforge.exception;

public class InvalidTaskStateTransitionException extends RuntimeException {

    public InvalidTaskStateTransitionException(String message) {
        super(message);
    }
}