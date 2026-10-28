package com.planetpulse.api.service;

import com.planetpulse.api.dto.response.CountryListResponse;
import com.planetpulse.api.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<CountryListResponse> getAllCountries() {
        return countryRepository.findAllForList();
    }
}
