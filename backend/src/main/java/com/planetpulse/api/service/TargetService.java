package com.planetpulse.api.service;

import com.planetpulse.api.dto.response.TargetResponse;
import com.planetpulse.api.entity.Targets;
import com.planetpulse.api.exception.ResourceNotFoundException;
import com.planetpulse.api.repository.TargetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TargetService {

    private final TargetRepository repository;

    public TargetService(TargetRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TargetResponse> findAll() {
        return repository.findAllWithCountry().stream()
                .map(this::targetResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TargetResponse findById(Integer id) {
        return repository.findByIdWithCountry(id)
                .map(this::targetResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Target not found"));
    }

    @Transactional(readOnly = true)
    public List<TargetResponse> findByIdCountry(Integer idCountry) {
        return repository.findByIdCountryWithCountry(idCountry).stream()
                .map(this::targetResponse)
                .toList();
    }


    // Conversion entité -> DTO
    private TargetResponse targetResponse(Targets t) {
        return new TargetResponse(
                t.getIdTarget(),
                t.getCountry().getCountryName(),
                t.getCountry().getIso(),
                t.getYear(),
                t.getCo2Emissions(),
                t.getPm25Emissions(),
                t.getForestArea(),
                t.getRenewableEnergyShare()
        );
    }
}
