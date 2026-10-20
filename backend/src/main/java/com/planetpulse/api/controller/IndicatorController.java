package com.planetpulse.api.controller;

import com.planetpulse.api.dto.IndicatorDto;
import com.planetpulse.api.dto.request.IndicatorCreateRequest;
import com.planetpulse.api.service.IndicatorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/indicators")
public class IndicatorController {

    private final IndicatorService indicatorService;

    @PostMapping
    public ResponseEntity<IndicatorDto> add(@Valid @RequestBody IndicatorCreateRequest request) {
        IndicatorDto indicator = indicatorService.addIndicator(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(indicator);
    }
}
