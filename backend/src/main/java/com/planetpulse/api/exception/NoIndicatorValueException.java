package com.planetpulse.api.exception;

public class NoIndicatorValueException extends RuntimeException{

    public NoIndicatorValueException(String message) {
        super("Fill in at least one indicator : " + message);
    }
}
