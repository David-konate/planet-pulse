package com.planetpulse.api.service;

import com.planetpulse.api.dto.request.RestCountryDto;
import com.planetpulse.api.entity.Country;
import com.planetpulse.api.entity.Region;
import com.planetpulse.api.entity.Subregion;
import com.planetpulse.api.exception.countries.CountriesAlreadyImportedException;
import com.planetpulse.api.exception.countries.ExternalApiException;
import com.planetpulse.api.repository.CountryRepository;
import com.planetpulse.api.repository.RegionRepository;
import com.planetpulse.api.repository.SubregionRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CountryImportService {

    private static final Logger log = LoggerFactory.getLogger(CountryImportService.class);

    private final RestCountriesService restCountriesService;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final SubregionRepository subregionRepository;
    private final Validator validator;

    public CountryImportService(RestCountriesService restCountriesService,
                                CountryRepository countryRepository,
                                RegionRepository regionRepository,
                                SubregionRepository subregionRepository,
                                Validator validator) {
        this.restCountriesService = restCountriesService;
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
        this.subregionRepository = subregionRepository;
        this.validator = validator;
    }

    @Transactional
    public int importCountries(){
        if (countryRepository.count() > 0) {
            throw new CountriesAlreadyImportedException("The countries have already been imported");
        }

        //fetch every page from REST Countries
        List<RestCountryDto> dtos = restCountriesService.fetchAll();

        // each region and subregion is created by once.
        Map<String, Region> regions = new HashMap<>();
        Map<String, Subregion> subregions = new HashMap<>();
        List<Country> countries = new ArrayList<>();

        for (RestCountryDto dto : dtos) {
            Set<ConstraintViolation<RestCountryDto>> violations = validator.validate(dto);
            if (!violations.isEmpty()) {
                log.warn("Country skipped: {} -> {}", dto,
                        violations.stream().map(ConstraintViolation::getMessage).toList());
                continue; // incomplete or inconsistent country: ignored
            }

            Region region = regions.computeIfAbsent(dto.region(), name ->
                    regionRepository.findByRegionIgnoreCase(name).orElseGet(() ->
                            regionRepository.save(new Region(name))));

            Subregion subregion = subregions.computeIfAbsent(dto.subregion(), name ->
                    subregionRepository.findBySubregionIgnoreCase(name).orElseGet(() ->
                            subregionRepository.save(new Subregion(name, region))));

            //The subregion name is unique in the database.
            // if one subregion > one region is false: then it stop and the transaction rollback.
            //Prevent the API to attach the subregion to another region.
            if (!subregion.getRegion().getRegion().equalsIgnoreCase(region.getRegion())){
                throw new ExternalApiException("Subregion " + subregion.getSubregion()
                        + "belongs to two regions: " + subregion.getRegion().getRegion()
                        + " and " + region.getRegion());
            }

            Country country = new Country();
            country.setIso(dto.codes().alpha2());
            country.setCountryName(dto.names().common());
            country.setFlagUrl(dto.flag().urlSvg());
            country.setAreaKilometers(dto.area().kilometers());
            country.setPopulation(dto.population());
            country.setSubregion(subregion);
            countries.add(country);
        }

        // Save and return the number of stored countries
        return countryRepository.saveAll(countries).size();
    }

    public long countImportedCountries(){
        return countryRepository.count();
    }
}
