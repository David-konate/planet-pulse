package com.planetpulse.api.service;

import com.planetpulse.api.dto.request.CreateTargetRequestDto;
import com.planetpulse.api.entity.Targets;
import com.planetpulse.api.exception.TargetExistsException;
import com.planetpulse.api.mapper.TargetMapper;
import com.planetpulse.api.repository.TargetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
// by default read only, add @transactional for write
@Transactional(readOnly = true)

public class TargetService {

   private final TargetRepository repository;

   private final TargetMapper mapper;


    public TargetService(TargetRepository repository, TargetMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Transactional
    public CreateTargetRequestDto createTarget(CreateTargetRequestDto request) throws TargetExistsException {

        if (repository.existsByCountryIdAndYear(request.getCountryId(), request.getYear())) {
            throw new TargetExistsException(request.getCountryId(), request.getYear());
        }

        Targets target = mapper.toEntity(request);
        Targets saved = repository.save(target);
        log.info("Target's create : id={}, pays={}, année={}",
                saved.getIdTarget(),
                saved.getCountryId(),
                saved.getYear());
        //return mapper.toResponse(saved);


        CreateTargetRequestDto ok = null;
        return ok;
    }
}
