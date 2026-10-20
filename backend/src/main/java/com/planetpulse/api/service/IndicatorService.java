package com.planetpulse.api.service;

import com.planetpulse.api.dto.IndicatorDto;
import com.planetpulse.api.entity.Country;
import com.planetpulse.api.entity.Indicator;
import com.planetpulse.api.exception.CountryNotFoundException;
import com.planetpulse.api.exception.FutureYearException;
import com.planetpulse.api.exception.IndicatorAlreadyExistsException;
import com.planetpulse.api.exception.NoIndicatorValueException;
import com.planetpulse.api.mapper.IndicatorMapper;
import com.planetpulse.api.payload.request.IndicatorRequest;
import com.planetpulse.api.repository.CountryRepository;
import com.planetpulse.api.repository.IndicatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;
    private final CountryRepository countryRepository;
    private final IndicatorMapper indicatorMapper;

    public IndicatorDto addIndicator(IndicatorRequest request) {
        // Rule: at least one indicator must be filled in
        if (hasNoValue(request)) {
           throw new NoIndicatorValueException("CO2 emissions, renewable energy, forest area or PM2.5 exposure.");
        }

        // RULE: The year must not be in the future
        int currentYear = java.time.Year.now().getValue();
        if (request.year() > currentYear) {
           throw new FutureYearException();
        }

        // Rule: the country must exist
        Country country = countryRepository.findById(request.countryId())
                .orElseThrow(() -> new CountryNotFoundException(request.countryId()));

        // Rule: only one row per country and year
        if (indicatorRepository.existsByCountryIdCountryAndYear(request.countryId(), request.year())) {
            throw new IndicatorAlreadyExistsException(country.getIdCountry(), request.year());
        }

        Indicator indicator = indicatorMapper.toEntity(request, country);
        Indicator saved = indicatorRepository.save(indicator);
        return indicatorMapper.toDto(saved);
    }

    private boolean hasNoValue(IndicatorRequest request) {
        return request.co2Emissions() == null
                && request.renewableEnergy() == null
                && request.forestArea() == null
                && request.pm25Exposure() == null;
    }

}
