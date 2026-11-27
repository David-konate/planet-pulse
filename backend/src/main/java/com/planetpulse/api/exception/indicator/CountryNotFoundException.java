package com.planetpulse.api.exception.indicator;

public class CountryNotFoundException extends RuntimeException{

<<<<<<< HEAD
    public CountryNotFoundException(String countryIso) {

        super("This country " + countryIso + " does not exist.");
=======
    public CountryNotFoundException(String iso) {

        super("This country "+ iso + " does not exist.");
>>>>>>> develop
    }
    
    
}
