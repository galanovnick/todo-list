package com.teamdev.javaclasses.service;

public class InvalidUserDataException extends Exception {
    private String message;

    public InvalidUserDataException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
