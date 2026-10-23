package com.planetpulse.api.service;

import com.planetpulse.api.dto.request.RestCountryDto;
import com.planetpulse.api.entity.Country;
import com.planetpulse.api.entity.Region;
import com.planetpulse.api.entity.Subregion;
import com.planetpulse.api.exception.CountriesAlreadyImportedException;
import com.planetpulse.api.repository.CountryRepository;
import com.planetpulse.api.repository.RegionRepository;
import com.planetpulse.api.repository.SubregionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class CountryImportService {

    private final RestCountriesService restCountriesService;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final SubregionRepository subregionRepository;

    //Same limits as the columns in countries create table sql.
    private static final int max_country_name = 50;
    private static final int max_flag_url = 50;
    private static final int max_region = 10;
    private static final int max_subregion = 30;

    public CountryImportService(RestCountriesService restCountriesService,
                                CountryRepository countryRepository,
                                RegionRepository regionRepository,
                                SubregionRepository subregionRepository) {
        this.restCountriesService = restCountriesService;
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
        this.subregionRepository = subregionRepository;
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
            if (!isComplete(dto)) {
                log.info("Country skipped (incomplete or too long): {}", dto);
                continue; // incomplete or inconsistent country: ignored
            }

            Region region = regions.computeIfAbsent(dto.region(), name ->
                    regionRepository.findByRegionIgnoreCase(name).orElseGet(() ->
                            regionRepository.save(new Region(name))));

            Subregion subregion = subregions.computeIfAbsent(dto.subregion(), name ->
                    subregionRepository.findBySubregionIgnoreCase(name).orElseGet(() ->
                            subregionRepository.save(new Subregion(name, region))));

            //The subregion name is unique in the database, so an existing one is reused: warn if its region differs
            if (!Objects.equals(subregion.getRegion().getId(), region.getId())){
                log.warn("Subregion '{}' already belongs to another region than '{}'",
                        subregion.getSubregion(), region.getRegion());
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

    //A country is kept only if every field is present and consistent and fits in database columns.
    private boolean isComplete(RestCountryDto dto) {
        return dto.names() != null && hasText(dto.names().common(), max_country_name)
                && dto.codes() != null && dto.codes().alpha2() != null
                && dto.codes().alpha2().matches("[A-Z]{2}")
                && dto.flag() != null && hasText(dto.flag().urlSvg(), max_flag_url)
                && hasText(dto.region(), max_region)
                && hasText(dto.subregion(), max_subregion)
                && dto.area() != null && dto.area().kilometers() != null
                && dto.area().kilometers().compareTo(BigDecimal.ZERO) > 0
                && dto.population() != null && dto.population() > 0;
    }

    private boolean hasText(String value, int maxLength) {
        return value != null && !value.isBlank() && value.length() <= maxLength;
    }
}
