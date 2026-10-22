package com.planetpulse.api.dto.request;

import com.planetpulse.api.validation.CurrentOrFutureYear;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateTargetRequestDto {

    // Setter: lets other classes WRITE the private field "countryId".
    // "this.countryId" is the class field, "countryId" (without "this") is the method parameter:
    // the parameter's value is copied into the field.
    // Getter: lets other classes (service, mapper...) READ the private field "countryId".
    // It simply returns the value currently stored in the field.
    @NotNull(message = "Cannot create a target that is not associated with a country")
    private Integer countryId;

    @NotNull(message = "A target must be associated with a year.")
    @CurrentOrFutureYear(message = "Year cannot be earlier than the current year")
    private Integer year;

    @Digits(integer = 13, fraction = 2, message = "CO2 emissions: max 13 digits before and 2 after the decimal point")
    @DecimalMin(value = "0.0", message = "CO2 emissions cannot be negative")
    private BigDecimal co2Emissions;

    @Digits(integer = 13, fraction = 2, message = "PM2.5 emissions: max 13 digits before and 2 after the decimal point")
    @DecimalMin(value = "0.0", message = "PM2.5 emissions cannot be negative")
    private BigDecimal pm25Emissions;

    @Digits(integer = 3, fraction = 2, message = "Forest area: max 3 digits before and 2 after the decimal point")
    @DecimalMin(value = "0.0", message = "Forest area must be >= 0")
    @DecimalMax(value = "100.0", message = "Forest area must be <= 100")
    private BigDecimal forestArea;

    @Digits(integer = 3, fraction = 2, message = "Renewable share: max 3 digits before and 2 after the decimal point")
    @DecimalMin(value = "0.0", message = "Renewable energy share must be >= 0")
    @DecimalMax(value = "100.0", message = "Renewable energy share must be <= 100")
    private BigDecimal renewableEnergyShare;


}