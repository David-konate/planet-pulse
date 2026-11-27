package com.planetpulse.api.exception.targets;

public class TargetExistsException extends RuntimeException {
    public TargetExistsException(String countryIso, Integer year) {
        super("A target already exists for the country " + countryIso + " and the year " + year);
    }
}