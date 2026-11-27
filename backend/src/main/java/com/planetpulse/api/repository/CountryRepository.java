package com.planetpulse.api.repository;

import com.planetpulse.api.dto.response.CountryListResponse;
import com.planetpulse.api.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("""
        SELECT new com.planetpulse.api.dto.response.CountryListResponse(
            c.iso, c.countryName, c.flagUrl, r.region)
        FROM Country c
        JOIN c.subregion s
        JOIN s.region r
        ORDER BY c.countryName
        """)
    List<CountryListResponse> findAllForList();

    Optional<Country> findByIso(String iso);
    boolean existsByIso(String iso);
}
