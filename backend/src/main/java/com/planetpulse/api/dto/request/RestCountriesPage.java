package com.planetpulse.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RestCountriesPage(Data data) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(List<RestCountryDto>objects, Meta meta) { }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Meta(int total, int count, int offset, boolean more) { }
}
