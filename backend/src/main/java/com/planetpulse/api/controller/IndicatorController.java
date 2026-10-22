package com.planetpulse.api.controller;

import com.planetpulse.api.dto.IndicatorDto;
import com.planetpulse.api.dto.request.IndicatorCreateRequest;
import com.planetpulse.api.dto.request.IndicatorTypeDto;
import com.planetpulse.api.service.IndicatorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/indicators")
public class IndicatorController {

    private final IndicatorService indicatorService;

    @GetMapping("/types")
    public List<IndicatorTypeDto> getIndicatorTypes() {
        return indicatorService.getIndicatorTypes();
    }

    @PostMapping
    public ResponseEntity<IndicatorDto> add(@Valid @RequestBody IndicatorCreateRequest request) {
        IndicatorDto indicator = indicatorService.addIndicator(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(indicator);
    }
}
