package com.planetpulse.api.exception.indicator;

public class FutureYearException extends RuntimeException{

    public FutureYearException() {
        super("The year cannot be in the future. Please provide the current or a past year.");
    }
}
