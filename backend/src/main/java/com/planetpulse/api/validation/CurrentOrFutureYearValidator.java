package com.planetpulse.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class CurrentOrFutureYearValidator
    // The two types within the angle brackets mean: "I am validating the `CurrentOrFutureYear`
        implements ConstraintValidator<CurrentOrFutureYear, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull on the field handles this case
        }
        return value >= Year.now().getValue();
    }
}