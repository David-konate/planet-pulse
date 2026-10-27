package com.planetpulse.api.exception;

public class NotTargetRecordingException extends RuntimeException {
    public NotTargetRecordingException() {
        super("At least one of the 4 targets must be documented in order to record a year.");
    }
}
