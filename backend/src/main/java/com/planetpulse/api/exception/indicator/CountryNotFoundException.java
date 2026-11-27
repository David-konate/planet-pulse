package com.planetpulse.api.exception.indicator;

public class CountryNotFoundException extends RuntimeException{

    public CountryNotFoundException(String iso) {

        super("This country "+ iso + " does not exist.");
    }
}
