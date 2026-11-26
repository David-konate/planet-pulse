package com.planetpulse.api.dto.response;

import java.math.BigDecimal;
import org.jspecify.annotations.Nullable;

public record TargetByIdCountryResponse(
        Integer idTarget,
        Integer countryId,
        Integer targetYear,
        String countryName,
        @Nullable BigDecimal co2Emissions,
        @Nullable BigDecimal pm25Emissions,
        @Nullable BigDecimal forestArea,
        @Nullable BigDecimal renewableEnergyShare
) {}