package com.planetpulse.api.validation;

import com.planetpulse.api.dto.request.IndicatorCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneIndicatorValidator implements ConstraintValidator<AtLeastOneIndicator, IndicatorCreateRequest> {

    @Override
    public boolean isValid(IndicatorCreateRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true; // a missing request is not the job of this validator
        }
        return request.co2Emissions() != null
                || request.renewableEnergy() != null
                || request.forestArea() != null
                || request.pm25Exposure() != null;
    }
}
