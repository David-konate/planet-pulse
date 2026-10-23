package com.planetpulse.api.exception.countries;

public class CountriesAlreadyImportedException extends RuntimeException {
    public CountriesAlreadyImportedException(String message) {
        super(message);
    }
}
