package com.planetpulse.api.exception;

public class IndicatorAlreadyExistsException extends RuntimeException{

    public IndicatorAlreadyExistsException(Integer countryId, Integer year) {
        super("Values already exist for this countryId "+ countryId + " and this year " + year);
    }
}
