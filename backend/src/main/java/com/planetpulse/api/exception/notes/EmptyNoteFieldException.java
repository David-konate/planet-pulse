package com.planetpulse.api.exception.notes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyNoteFieldException extends RuntimeException {
    public EmptyNoteFieldException() {
        super("The field cannot be empty.");
    }
}