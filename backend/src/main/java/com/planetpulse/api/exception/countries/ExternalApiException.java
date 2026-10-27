package com.planetpulse.api.exception.countries;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message) {
        super(message);
    }

    //keeps the original exception so the stack trace shows the real problem
    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
