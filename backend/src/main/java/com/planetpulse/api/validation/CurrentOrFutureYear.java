package com.planetpulse.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrentOrFutureYearValidator.class)  // la classe qui fait le test
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

// Keeps the annotation in memory during execution,
// allowing Spring to read it. Without this, it would disappear at compilation.
public @interface CurrentOrFutureYear {

    String message() default "Year cannot be earlier than the current year";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}