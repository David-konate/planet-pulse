package com.planetpulse.api.exception.indicator;

public class CountryNotFoundException extends RuntimeException{

    public CountryNotFoundException(Integer countryId) {

        super("This country "+ countryId + " does not exist.");
    }
}
