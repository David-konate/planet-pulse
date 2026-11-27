package com.planetpulse.api.exception.indicator;

public class IndicatorAlreadyExistsException extends RuntimeException{

    public IndicatorAlreadyExistsException(String iso, Integer year) {
        super("Values already exist for this countryId "+ iso + " and this year " + year);
    }
}
