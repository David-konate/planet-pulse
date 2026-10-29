package com.planetpulse.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.planetpulse.api.validation.AtLeastOneIndicator;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@AtLeastOneIndicator
public record IndicatorCreateRequest(

        @JsonProperty("countryId")
        @NotNull(message = "The country is required.")
        @Positive(message = "The country is not valid.")
        Integer countryId,

        // Year range to confirm with the PO
        @JsonProperty("yearIndicator")
        @NotNull(message = "The year is required.")
        Integer yearIndicator,

        @JsonProperty("co2Emissions")
        // The 4 indicators are optional, but at least one is needed (checked in the service)
        @DecimalMin(value = "0", message = "CO₂ emissions must be 0 or more.")
        BigDecimal co2Emissions,

        @JsonProperty("renewableEnergy")
        @DecimalMin(value = "0", message = "Renewable energy share must be between 0 and 100.")
        @DecimalMax(value = "100", message = "Renewable energy share must be between 0 and 100.")
        BigDecimal renewableEnergy,

        @JsonProperty("forestArea")
        @DecimalMin(value = "0", message = "Forest area must be between 0 and 100.")
        @DecimalMax(value = "100", message = "Forest area must be between 0 and 100.")
        BigDecimal forestArea,

        @JsonProperty("pm25Exposure")
        @DecimalMin(value = "0", message = "PM2.5 exposure must be 0 or more.")
        BigDecimal pm25Exposure

) { }
