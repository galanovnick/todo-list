package com.teamdev.javaclasses.service;

public class InvalidDescriptionException extends Exception {

    private final String message;

    public InvalidDescriptionException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
