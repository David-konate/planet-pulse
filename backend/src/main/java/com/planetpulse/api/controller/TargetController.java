package com.planetpulse.api.controller;

import com.planetpulse.api.dto.response.TargetResponse;
import com.planetpulse.api.service.TargetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/targets")
public class TargetController {

    private final TargetService service;

    public TargetController(TargetService service) {
        this.service = service;
    }

    @GetMapping
    public List<TargetResponse> getAll() {
        return service.findAll();
    }
}
