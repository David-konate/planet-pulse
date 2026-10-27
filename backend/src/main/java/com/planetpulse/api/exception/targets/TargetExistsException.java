package com.planetpulse.api.exception.targets;

public class TargetExistsException extends RuntimeException {
    public TargetExistsException(Integer countryId, Integer year) {
        super("A target already exists for the country " + countryId + " and the year " + year);
    }
}