package com.planetpulse.api.exception.notes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoteCountryNotFoundException extends RuntimeException {

    public NoteCountryNotFoundException(String message) {
        super(message);
    }
}
