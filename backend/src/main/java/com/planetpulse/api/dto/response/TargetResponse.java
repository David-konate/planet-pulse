package com.planetpulse.api.dto.response;

import java.math.BigDecimal;

// DTO de sortie : ce que l'API renvoie en JSON (pas l'entité JPA)
public record TargetResponse(
        Integer idTarget,
        String countryName,
        String iso,
        Integer year,
        BigDecimal co2Emissions,
        BigDecimal pm25Emissions,
        BigDecimal forestArea,
        BigDecimal renewableEnergyShare
) {}
