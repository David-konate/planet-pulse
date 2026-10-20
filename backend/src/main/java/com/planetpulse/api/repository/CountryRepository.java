package com.planetpulse.api.repository;
import com.planetpulse.api.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

    public interface CountryRepository extends JpaRepository<Country, Integer> {}
