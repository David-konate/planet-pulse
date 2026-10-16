package com.planetpulse.api.controller;

import com.planetpulse.api.dto.response.TargetResponse;
import com.planetpulse.api.service.TargetService;
import org.springframework.web.bind.annotation.PathVariable;
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


    // @PathVariable récupère une valeur dans l’URL et la passe en paramètre à ta méthode Java.
    @GetMapping("/{id}")
    public TargetResponse getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/country/{idCountry}")
    //ajouté une vérification sur lexistence du pays via un apele au controlleur pays
    public List<TargetResponse> getByIdCountry(@PathVariable Integer idCountry) {
        return service.findByIdCountry(idCountry);
    }
}
