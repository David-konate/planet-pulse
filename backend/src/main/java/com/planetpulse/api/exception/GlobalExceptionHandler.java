package com.planetpulse.api.exception;

import com.planetpulse.api.payload.response.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoIndicatorValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNoIndicatorValue(NoIndicatorValueException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCountryNotFound(CountryNotFoundException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(IndicatorAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleIndicatorAlreadyExists(IndicatorAlreadyExistsException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    // A field of the request is invalid (see the annotations in IndicatorRequest)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidData(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ErrorResponse(message);
    }

    // The database refused the data (a constraint was broken)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDatabaseRule(DataIntegrityViolationException exception) {
        return new ErrorResponse("The values could not be saved. Please check the data.");
    }

    @ExceptionHandler(FutureYearException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFutureYear(FutureYearException exception) {
        return new ErrorResponse(exception.getMessage());
    }

}
