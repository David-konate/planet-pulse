package com.planetpulse.api.service;

import com.planetpulse.api.dto.request.TargetCreateRequest;
import com.planetpulse.api.entity.Targets;
import com.planetpulse.api.exception.NotTargetRecordingException;
import com.planetpulse.api.exception.TargetExistsException;
import com.planetpulse.api.exception.indicator.FutureYearException;
import com.planetpulse.api.mapper.TargetMapper;
import com.planetpulse.api.repository.TargetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;

@Slf4j
@Service
// lecture seule par défaut, @Transactional sur les méthodes d'écriture
@Transactional(readOnly = true)
public class TargetService {

    private final TargetRepository repository;
    private final TargetMapper mapper;

    public TargetService(TargetRepository repository, TargetMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public TargetCreateRequest createTarget(TargetCreateRequest request) {
        checkBusinessRules(request);

        Targets target = mapper.toEntity(request);
        Targets saved;
        try {
            saved = repository.save(target);
        } catch (DataIntegrityViolationException e) {
            throw new TargetExistsException(request.countryId(), request.targetYear());
        }

        log.info("Target created : id={}, country={}, year={}",
                saved.getIdTarget(),
                saved.getCountryId(),
                saved.getTargetYear());

        // TODO: remplacer par toResponse(saved) quand un DTO de réponse existera
        return request;
    }

    private void checkBusinessRules(TargetCreateRequest request) {
        if (request.targetYear() < Year.now().getValue()) {
            throw new FutureYearException();
        }

        if (request.co2Emissions() == null
                && request.forestArea() == null
                && request.pm25Emissions() == null
                && request.renewableEnergyShare() == null) {
            throw new NotTargetRecordingException();
        }

        if (repository.existsByCountryIdAndTargetYear(request.countryId(), request.targetYear())) {
            throw new TargetExistsException(request.countryId(), request.targetYear());
        }
    }
}