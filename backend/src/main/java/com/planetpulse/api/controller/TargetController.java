package com.planetpulse.api.controller;

import com.planetpulse.api.dto.request.CreateTargetRequestDto;
import com.planetpulse.api.service.TargetService;
import jakarta.validation.Valid;
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




    // @PathVariable récupère une valeur dans l’URL et la passe en paramètre à ta méthode Java.
    @PostMapping
    public ResponseEntity<CreateTargetRequestDto> create(
            //@valid = in connection with the DTO control annotations
            @Valid @RequestBody CreateTargetRequestDto request) {
        CreateTargetRequestDto target = service.createTarget(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(target);
    }


}
