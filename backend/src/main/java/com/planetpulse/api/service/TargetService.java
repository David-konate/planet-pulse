package com.planetpulse.api.service;

import com.planetpulse.api.dto.request.TargetCreateRequest;
import com.planetpulse.api.entity.Targets;
import com.planetpulse.api.mapper.TargetMapper;
import com.planetpulse.api.repository.TargetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
// read only by default, add @Transactional on write methods
@Transactional(readOnly = true)
public class TargetService {

    private final TargetRepository repository;
    private final TargetMapper mapper;

    public TargetService(TargetRepository repository, TargetMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public boolean existsTarget(Integer countryId, Integer year) {
        return repository.existsByCountryIdAndTargetYear(countryId, year);
    }

    @Transactional
    public TargetCreateRequest createTarget(TargetCreateRequest request) {
        Targets target = mapper.toEntity(request);
        Targets saved = repository.save(target);

        log.info("Target created : id={}, country={}, year={}",
                saved.getIdTarget(),
                saved.getCountryId(),
                saved.getTargetYear());
        // TODO: replace by mapper.toResponse(saved) once a response DTO exists
        return request;
    }
}