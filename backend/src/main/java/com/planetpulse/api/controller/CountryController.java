package com.planetpulse.api.controller;

import com.planetpulse.api.dto.response.CountryListResponse;
import com.planetpulse.api.dto.response.ImportResponse;
import com.planetpulse.api.dto.response.ImportStatusResponse;
import com.planetpulse.api.service.CountryImportService;
import com.planetpulse.api.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryImportService importService;
    private final CountryService countryService;

    public CountryController(CountryImportService importService, CountryService countryService) {
        this.importService = importService;
        this.countryService = countryService;
    }

    @PostMapping("/import")
    public ResponseEntity<ImportResponse> importCountries() {
        int count = importService.importCountries();
        ImportResponse body = new ImportResponse(count, count + " countries imported");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/import/status")
    public ImportStatusResponse getImportCountriesStatus(){
        long count = importService.countImportedCountries();
        return new ImportStatusResponse(count > 0, count);
    }

    @GetMapping
    public List<CountryListResponse> listCountries() {
        return countryService.getAllCountries();
    }
}
