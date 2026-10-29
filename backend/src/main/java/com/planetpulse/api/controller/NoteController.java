// STEP 1
// Reception of HTTP POST/api/notes, ENTRY POINT of note creation
// Will receive JSON data sent by frontend and ask service to create note
package com.planetpulse.api.controller;

import com.planetpulse.api.dto.request.NoteCreateRequest;
import com.planetpulse.api.entity.Note;
import com.planetpulse.api.service.NoteService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Tells Spring that this class handles HTTP requests and returns data as JSON
@RequestMapping("/api/notes") // Defines the beginning of the URL
public class NoteController {
    //Retrieve a copy of NoteService
    private final NoteService service;

    // Constructor: give access of NoteService created up there
    public NoteController(NoteService service) {
        this.service = service;
    }

    // indicate that this method treat a POST query
    @PostMapping
    public ResponseEntity<NoteCreateRequest> create(
            // @Valid = trigger validations define on the received object
            // @ equestBody = Ask spring to convert the JSON file in a Java object
            @Valid @RequestBody NoteCreateRequest request) {

        // Ask the service to create the note
        NoteCreateRequest note = service.createNote(request);

        // Return HTTP 201 Created with the created note
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }
}
