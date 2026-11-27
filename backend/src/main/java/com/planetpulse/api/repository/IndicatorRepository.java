package com.planetpulse.api.repository;

import com.planetpulse.api.entity.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndicatorRepository extends JpaRepository<Indicator, Integer> {

    boolean existsByCountryIsoAndYearIndicator(String iso, Integer yearIndicator);

    List<Indicator> findByCountryIsoOrderByYearIndicatorDesc(String iso);
}
