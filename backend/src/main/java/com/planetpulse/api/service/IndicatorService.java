package com.planetpulse.api.service;

import com.planetpulse.api.dto.IndicatorDto;
import com.planetpulse.api.dto.request.IndicatorCreateRequest;
import com.planetpulse.api.dto.request.IndicatorTypeDto;
import com.planetpulse.api.entity.Country;
import com.planetpulse.api.entity.Indicator;
import com.planetpulse.api.enums.IndicatorType;
import com.planetpulse.api.exception.indicator.CountryNotFoundException;
import com.planetpulse.api.exception.indicator.FutureYearException;
import com.planetpulse.api.exception.indicator.IndicatorAlreadyExistsException;
import com.planetpulse.api.exception.indicator.NoIndicatorValueException;
import com.planetpulse.api.mapper.IndicatorMapper;
import com.planetpulse.api.repository.CountryRepository;
import com.planetpulse.api.repository.IndicatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;
    private final CountryRepository countryRepository;
    private final IndicatorMapper indicatorMapper;

    public IndicatorDto addIndicator(IndicatorCreateRequest request) {
        // Rule: at least one indicator must be filled in
        if (hasNoValue(request)) {
           throw new NoIndicatorValueException("CO2 emissions, renewable energy, forest area or PM2.5 exposure.");
        }

        // RULE: The year must not be in the future
        int currentYear = java.time.Year.now().getValue();
        if (request.yearIndicator() > currentYear) {
           throw new FutureYearException();
        }

        // Rule: the country must exist
        Country country = countryRepository.findById(request.countryId())
                .orElseThrow(() -> new CountryNotFoundException(request.countryId()));

        // Rule: only one row per country and year
        if (indicatorRepository.existsByCountryIdCountryAndYearIndicator(request.countryId(), request.yearIndicator())) {
            throw new IndicatorAlreadyExistsException(country.getIdCountry(), request.yearIndicator());
        }

        Indicator indicator = indicatorMapper.toEntity(request, country);
        Indicator saved = indicatorRepository.save(indicator);
        return indicatorMapper.toDto(saved);
    }

    // The list of indicators used to build the form
    public List<IndicatorTypeDto> getIndicatorTypes() {
        return Arrays.stream(IndicatorType.values())
                .map(type -> new IndicatorTypeDto(type.getField(), type.getLabel(), type.getUnit(), type.getMax()))
                .toList();
    }

    private boolean hasNoValue(IndicatorCreateRequest request) {
        return request.co2Emissions() == null
                && request.renewableEnergy() == null
                && request.forestArea() == null
                && request.pm25Exposure() == null;
    }

}
