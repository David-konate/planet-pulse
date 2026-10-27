// STEP 1
// reception of http query POST, entry point of note creation
// will receive data send by frontend and ask service to create note
package com.planetpulse.api.controller;

import com.planetpulse.api.dto.request.NoteCreateRequest;
import com.planetpulse.api.entity.Note;
import com.planetpulse.api.service.NoteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController // say to spring that this class handling http query and send them in JSON
@RequestMapping("/api/notes") // define the begining of the URL
public class NoteController {

    private final NoteService service;

    public NoteController(NoteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Note> create(
            @Valid @RequestBody NoteCreateRequest request) {

        log.info("Create note request: {}", request);

        Note note = service.createNote(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }
}
