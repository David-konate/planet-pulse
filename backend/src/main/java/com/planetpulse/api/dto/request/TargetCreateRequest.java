package com.planetpulse.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planetpulse.api.validation.CurrentOrFutureYear;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TargetCreateRequest(

        @JsonProperty("countryId")
        @NotNull(message = "Cannot create a target that is not associated with a country")
        Integer countryId,

        @JsonProperty("targetYear")
        @NotNull(message = "A target must be associated with a year.")
        @CurrentOrFutureYear
        Integer targetYear,

        @JsonProperty("co2Emissions")
        @Digits(integer = 13, fraction = 2, message = "CO2 emissions: max 13 digits before and 2 after the decimal point")
        @DecimalMin(value = "0.0", message = "CO2 emissions cannot be negative")
        BigDecimal co2Emissions,

        @JsonProperty("pm25Emissions")
        @Digits(integer = 13, fraction = 2, message = "PM2.5 emissions: max 13 digits before and 2 after the decimal point")
        @DecimalMin(value = "0.0", message = "PM2.5 emissions cannot be negative")
        BigDecimal pm25Emissions,

        @JsonProperty("forestArea")
        @Digits(integer = 3, fraction = 2, message = "Forest area: max 3 digits before and 2 after the decimal point")
        @DecimalMin(value = "0.0", message = "Forest area must be >= 0")
        @DecimalMax(value = "100.0", message = "Forest area must be <= 100")
        BigDecimal forestArea,

        @JsonProperty("renewableEnergyShare")
        @Digits(integer = 3, fraction = 2, message = "Renewable share: max 3 digits before and 2 after the decimal point")
        @DecimalMin(value = "0.0", message = "Renewable energy share must be >= 0")
        @DecimalMax(value = "100.0", message = "Renewable energy share must be <= 100")
        BigDecimal renewableEnergyShare

) { }