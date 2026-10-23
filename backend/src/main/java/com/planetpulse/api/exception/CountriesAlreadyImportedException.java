package com.planetpulse.api.exception;

public class CountriesAlreadyImportedException extends RuntimeException {
    public CountriesAlreadyImportedException(String message) {
        super(message);
    }
}
