package com.planetpulse.api.repository;

import com.planetpulse.api.entity.Targets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TargetRepository extends JpaRepository<Targets, Integer> {

    // JOIN FETCH : charge le pays dans la même requête (évite une requête par ligne)
    @Query("select t from Targets t join fetch t.country c order by c.countryName, t.year")
    List<Targets> findAllWithCountry();
}
