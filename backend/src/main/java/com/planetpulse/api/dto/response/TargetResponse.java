package com.planetpulse.api.dto.response;


import com.planetpulse.api.entity.Targets;

import java.math.BigDecimal;

public record TargetResponse(
        Integer idTarget,
        Integer countryId,
        Integer targetYear,
        BigDecimal co2Emissions,
        BigDecimal pm25Emissions,
        BigDecimal forestArea,
        BigDecimal renewableEnergyShare
) {
    public static TargetResponse from(Targets target) {
        return new TargetResponse(
                target.getIdTarget(),
                target.getCountryId(),
                target.getTargetYear(),
                target.getCo2Emissions(),
                target.getPm25Emissions(),
                target.getForestArea(),
                target.getRenewableEnergyShare()
        );
    }
}