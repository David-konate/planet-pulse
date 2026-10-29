package com.planetpulse.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RestCountryDto(

    @NotNull(message = "Names are missing")
    @Valid
    Names names,

    @NotNull(message = "Codes are missing")
    @Valid
    Codes codes,

    @NotNull(message = "Flag is missing")
    @Valid
    Flag flag,

    @NotBlank(message = "Region is missing")
    @Size(max = 10, message = "Region: max 10 characters")
    String region,

    @NotBlank(message = "Subregion is missing")
    @Size(max = 30, message = "Subregion: max 30 characters")
    String subregion,

    @NotNull(message = "Area is missing")
    @Valid
    Area area,

    @NotNull(message = "Population is missing")
    @Positive(message = "Population must be > 0")
    Integer population) {

    public record Names (
            @NotBlank(message = "Country name is missing")
            @Size(max = 50, message = "Country name: max 50 characters")
            String common) { }

    public record Codes (
            @JsonProperty("alpha_2")
            @NotNull(message = "ISO code is missing")
            @Pattern(regexp = "[A-Z]{2}", message = "ISO code must be 2 uppercase letters")
            String alpha2) { }

    public record Flag (
            @JsonProperty("url_svg")
            @NotBlank(message = "Flag url is missing")
            @Size(max = 50, message = "Flag url: max 50 characters")
            String urlSvg) { }

    public record Area (
            @NotNull(message = "Area in kilometers is missing")
            @Positive(message = "Area must be > 0")
            BigDecimal kilometers) { }

}
