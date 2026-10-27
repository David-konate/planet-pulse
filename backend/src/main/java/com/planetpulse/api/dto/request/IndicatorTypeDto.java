package com.planetpulse.api.dto.request;

public record IndicatorTypeDto(
        String field,
        String label,
        String unit,
        Integer max
) { }
