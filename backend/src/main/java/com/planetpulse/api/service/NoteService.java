// STEP 2
// Business logic, handling the creation of the note
package com.planetpulse.api.service;

import com.planetpulse.api.dto.request.NoteCreateRequest;
import com.planetpulse.api.entity.Country;
import com.planetpulse.api.entity.Note;
import com.planetpulse.api.mapper.NoteMapper;
import com.planetpulse.api.repository.CountryRepository;
import com.planetpulse.api.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
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
        // check the country associated to the note from id
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Country not found: " + request.getCountryId()));
        //Mapper change data to object Note
        Note note = mapper.toEntity(request);
        // The note associated to the country receive the current date and time
        note.setCountry(country);
        note.setNoteTimestamp(LocalDateTime.now());

        Note saved = repository.save(note);

        log.info("Note created: timestamp={}, country={}",
                saved.getNoteTimestamp(),
                saved.getCountry().getIdCountry());

        return saved;
    }
}
