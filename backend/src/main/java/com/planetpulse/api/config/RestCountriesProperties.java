package com.planetpulse.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "restcountries")
public record RestCountriesProperties(String baseUrl, String apiKey) {
    //
}
