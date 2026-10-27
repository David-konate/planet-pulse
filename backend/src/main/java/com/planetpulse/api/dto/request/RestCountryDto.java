package com.planetpulse.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RestCountryDto(
    Names names,
    Codes codes,
    Flag flag,
    String region,
    String subregion,
    Area area,
    Integer population) {

    public record Names (String common) { }
    public record Codes (@JsonProperty("alpha_2") String alpha2) { }
    public record Flag (@JsonProperty("url_svg") String urlSvg) { }
    public record Area (BigDecimal kilometers) { }

}
