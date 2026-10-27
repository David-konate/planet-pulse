package com.planetpulse.api.repository;
import com.planetpulse.api.entity.Subregion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubregionRepository extends JpaRepository<Subregion, Integer> {
    Optional<Subregion> findBySubregionIgnoreCase(String subregion);
}