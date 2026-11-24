package com.planetpulse.api.repository;

import com.planetpulse.api.entity.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndicatorRepository extends JpaRepository<Indicator, Integer> {

    boolean existsByCountryIdCountryAndYearIndicator(Integer idCountry, Integer yearIndicator);
    List<Indicator> findByCountryIdCountryOrderByYearIndicatorDesc(Integer idCountry);
}
