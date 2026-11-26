package com.planetpulse.api.service;

import com.planetpulse.api.dto.response.TargetByIdCountryResponse;
import com.planetpulse.api.dto.request.TargetCreateRequest;
import com.planetpulse.api.entity.Country;
import com.planetpulse.api.entity.Targets;
import com.planetpulse.api.exception.indicator.CountryNotFoundException;
import com.planetpulse.api.exception.indicator.FutureYearException;
import com.planetpulse.api.exception.targets.NotTargetRecordingException;
import com.planetpulse.api.exception.targets.TargetExistsException;
import com.planetpulse.api.exception.targets.TargetNotFoundException;
import com.planetpulse.api.mapper.TargetMapper;
import com.planetpulse.api.repository.CountryRepository;
import com.planetpulse.api.repository.TargetRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@Service
// lecture seule par défaut, @Transactional sur les méthodes d'écriture
@Transactional(readOnly = true)
public class TargetService {

    private final TargetRepository repository;
    private final CountryRepository countryRepository;
    private final TargetMapper mapper;

    public TargetService(TargetRepository repository,
                         CountryRepository countryRepository,
                         TargetMapper mapper) {
        this.repository = repository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    @Transactional
    public TargetCreateRequest createTarget(TargetCreateRequest request) {
        checkBusinessRulesCreate(request);

        Targets target = mapper.toEntity(request);
        try {
            repository.save(target);
        } catch (DataIntegrityViolationException e) {
            throw new TargetExistsException(request.countryId(), request.targetYear());
        }

        // TODO: remplacer par toResponse(saved) quand un DTO de réponse existera
        return request;
    }



    private void checkBusinessRulesCreate(TargetCreateRequest request) {
        if (!countryRepository.existsById(request.countryId())) {
            throw new CountryNotFoundException(request.countryId());
        }

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

    public List<TargetByIdCountryResponse> getTargetsByCountryId(Integer countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId));

        List<Targets> targets = repository.findByCountryIdOrderByTargetYear(countryId);

        if (targets.isEmpty()) {
            throw new TargetNotFoundException(country.getCountryName());
        }

        return targets.stream()
                .map(target -> mapper.toByCountryResponse(target, country.getCountryName()))
                .toList();
    }



}