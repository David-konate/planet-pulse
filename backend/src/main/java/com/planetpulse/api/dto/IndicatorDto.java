package com.planetpulse.api.dto;

import java.math.BigDecimal;

public record IndicatorDto(
        Integer idIndicator,
        Integer countryId,
        Integer year,
        BigDecimal co2Emissions,
        BigDecimal renewableEnergy,
        BigDecimal forestArea,
        BigDecimal pm25Exposure
) { }
