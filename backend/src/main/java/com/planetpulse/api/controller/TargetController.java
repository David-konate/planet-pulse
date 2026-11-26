package com.planetpulse.api.controller;

import com.planetpulse.api.dto.request.TargetCreateRequest;
import com.planetpulse.api.dto.response.TargetByIdCountryResponse;
import com.planetpulse.api.service.TargetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/targets")

public class TargetController {

    private final TargetService service;

    public TargetController(TargetService service) {
        this.service = service;
    }

    // @Valid = déclenche les validations de format déclarées sur le DTO
    // Les règles métier sont gérées dans le service
    @PostMapping
    public ResponseEntity<TargetCreateRequest> create(
            @Valid @RequestBody TargetCreateRequest request) {
        TargetCreateRequest created = service.createTarget(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/country/{countryIso}")
    public ResponseEntity<List<TargetByIdCountryResponse>> getByCountryIso(
            @PathVariable @Pattern (regexp = "^[A-Za-z]{2}$") String countryIso) {
        return ResponseEntity.ok(service.getTargetsByCountryIso(countryIso.toUpperCase()));
    }


}