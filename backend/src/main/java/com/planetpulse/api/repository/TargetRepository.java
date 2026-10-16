package com.planetpulse.api.repository;

import com.planetpulse.api.entity.Targets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TargetRepository extends JpaRepository<Targets, Integer> {

    // JOIN FETCH : charge le pays dans la même requête (évite une requête par ligne)
    @Query("select t from Targets t join fetch t.country c order by c.countryName, t.year")
    List<Targets> findAllWithCountry();

    // Un seul target avec son pays. Optional : vide si l'id n'existe pas
    @Query("select t from Targets t join fetch t.country where t.idTarget = :id")
    Optional<Targets> findByIdWithCountry(@Param("id") Integer id);

    @Query("select t from Targets t join fetch t.country c where c.idCountry = :idCountry order by t.year")
    List<Targets> findByIdCountryWithCountry(@Param("idCountry") Integer idCountry);
}