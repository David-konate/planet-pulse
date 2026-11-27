// STEP 3
// Business logic treatment, handling the creation of the note with the rules set before
// Answering to what are the operation needed to creat a note :
// - the service search the corresponding country
// -prepare the note
// - ask to save
package com.planetpulse.api.service;

import com.planetpulse.api.dto.request.NoteCreateRequest;
import com.planetpulse.api.entity.Country;
import com.planetpulse.api.entity.Note;
import com.planetpulse.api.mapper.NoteMapper;
import com.planetpulse.api.repository.CountryRepository;
import com.planetpulse.api.repository.NoteRepository;
import com.planetpulse.api.exception.notes.NoteCountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Facade server that goes in the back
@Service
@Transactional(readOnly = true)
public class NoteService {

    private final NoteRepository repository;
    private final CountryRepository countryRepository;
    private final NoteMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(NoteService.class);

    public NoteService(
            NoteRepository repository,
            CountryRepository countryRepository,
            NoteMapper mapper) {
        this.repository = repository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    @Transactional
    public NoteCreateRequest createNote(NoteCreateRequest request) {

        String iso = request.countryIso().toUpperCase();
        Country country = countryRepository.findByIso(iso)
                .orElseThrow(() -> new NoteCountryNotFoundException("Country not found: " + iso));

        //Mapper change data to object Note
        //Convert the request into a Note object
        Note note = mapper.toEntity(request);
        // Set the country ISO
        note.setCountryIso(country.getIso());
        // Set the current date and time
        note.setNoteTimestamp(LocalDateTime.now().withNano(0));
        // Set a condition : if the user do not enter a date set the current date
        note.setNoteDate(
                request.noteDate() != null
                        ? request.noteDate()
                        : LocalDate.now()
        );
        // Save the note
        Note saved = repository.save(note);



        log.info("Note created: timestamp="
                + saved.getNoteTimestamp()
                + ", country="
                + iso);

        // to change with method to saved when a dto of response
        return request;
    }
}
