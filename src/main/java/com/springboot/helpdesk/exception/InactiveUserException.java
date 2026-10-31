package com.springboot.helpdesk.exception;

public class InactiveUserException extends RuntimeException {
    public InactiveUserException(String message) {
        super(message);
    }
}
