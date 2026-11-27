package com.planetpulse.api.exception.indicator;

public class CountryNotFoundException extends RuntimeException{

    public CountryNotFoundException(String countryIso) {

        super("This country " + countryIso + " does not exist.");
    }
    
    
}
