package com.planetpulse.api.exception.targets;

public class TargetNotFoundException extends RuntimeException {
    public TargetNotFoundException(String countryName) {
        super("No target found for the country " + countryName);
    }

    public TargetNotFoundException(Integer countryId, Integer targetYear) {
        super("No target found for the country " + countryId + " and the year " + targetYear);
    }
}