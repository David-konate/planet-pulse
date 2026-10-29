package com.planetpulse.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE) // applied to the class level
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneIndicatorValidator.class)
public @interface AtLeastOneIndicator {

    String message() default "Fill in at least one indicator.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
