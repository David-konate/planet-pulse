package com.planetpulse.api.dto;

import java.math.BigDecimal;

public record IndicatorDto(
        Integer idIndicator,
        Integer countryId,
        Integer yearIndicator,
        BigDecimal co2Emissions,
        BigDecimal renewableEnergy,
        BigDecimal forestArea,
        BigDecimal pm25Exposure
) { }
