// STEP 3
// Business logic, handling the creation of the note
package com.planetpulse.api.service;

import com.planetpulse.api.dto.request.NoteCreateRequest;
import com.planetpulse.api.entity.Country;
import com.planetpulse.api.entity.Note;
import com.planetpulse.api.mapper.NoteMapper;
import com.planetpulse.api.repository.CountryRepository;
import com.planetpulse.api.repository.NoteRepository;
import com.planetpulse.api.exception.notes.NoteCountryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional(readOnly = true)
public class NoteService {

    private final NoteRepository repository;
    private final CountryRepository countryRepository;
    private final NoteMapper mapper;

    public NoteService(
            NoteRepository repository,
            CountryRepository countryRepository,
            NoteMapper mapper) {
        this.repository = repository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Note createNote(NoteCreateRequest request) {


        countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new NoteCountryNotFoundException(
                        "Country not found: " + request.getCountryId()
                ));

        //Mapper change data to object Note
        //Convert the request into a Note object
        Note note = mapper.toEntity(request);
        // Set the country ID
        note.setCountryId(request.getCountryId());
        // Set the current date and time
        note.setNoteTimestamp(LocalDateTime.now().withNano(0));
        // Save the note
        Note saved = repository.save(note);

        System.out.println("Note created: timestamp="
                + saved.getNoteTimestamp()
                + ", country="
                + saved.getCountryId());

        return saved;
    }
}
