package com.planetpulse.api.repository;

import com.planetpulse.api.entity.Targets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TargetRepository extends JpaRepository<Targets, Integer> {

    // "existsBy" + "CountryId" + "And" + "Year"
    // -> SELECT ... WHERE id_country = ? AND year_ = ?
    boolean existsByCountryIdAndTargetYear(Integer countryId, Integer targetYear);}