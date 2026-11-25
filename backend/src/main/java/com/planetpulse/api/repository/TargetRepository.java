package com.planetpulse.api.repository;

import com.planetpulse.api.entity.Targets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TargetRepository extends JpaRepository<Targets, Integer> {

    boolean existsByCountryIdAndTargetYear(Integer countryId, Integer targetYear);

    List<Targets> findByCountryIdOrderByTargetYear(Integer countryId);

}