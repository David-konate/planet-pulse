package com.planetpulse.api.repository;

import com.planetpulse.api.entity.Targets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TargetRepository extends JpaRepository<Targets, Integer> {

    boolean existsByCountryIsoAndTargetYear(String countryIso, Integer targetYear);

    List<Targets> findByCountryIsoOrderByTargetYear(String countryIso);
}