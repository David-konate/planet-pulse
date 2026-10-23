package com.planetpulse.api.controller;

import com.planetpulse.api.dto.request.TargetCreateRequest;
import com.planetpulse.api.exception.NotTargetRecordingException;
import com.planetpulse.api.exception.TargetExistsException;
import com.planetpulse.api.exception.indicator.FutureYearException;
import com.planetpulse.api.service.TargetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import java.time.Year;
@Slf4j

@RestController
@RequestMapping("/api/targets")
public class TargetController {

    private final TargetService service;

    public TargetController(TargetService service) {
        this.service = service;
    }

    // @Valid = triggers the validation annotations declared on the DTO
    @PostMapping
    public ResponseEntity<TargetCreateRequest> create(
            @Valid @RequestBody TargetCreateRequest request) {
        log.info("Create target request: {}", request);
        // Business checks done at controller level
        if (service.existsTarget(request.getCountryId(), request.getTargetYear())) {
            throw new TargetExistsException(request.getCountryId(), request.getTargetYear());
        }

        if (request.getTargetYear() < Year.now().getValue()) {
            throw new FutureYearException();
        }

        if (request.getCo2Emissions() == null
                && request.getForestArea() == null
                && request.getPm25Emissions() == null
                && request.getRenewableEnergyShare() == null) {
            throw new NotTargetRecordingException();
        }

        TargetCreateRequest target = service.createTarget(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(target);
    }
}