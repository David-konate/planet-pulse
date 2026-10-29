package com.planetpulse.api.exception;

import com.planetpulse.api.dto.response.ErrorResponse;
import com.planetpulse.api.exception.countries.CountriesAlreadyImportedException;
import com.planetpulse.api.exception.countries.ExternalApiException;
import com.planetpulse.api.exception.indicator.CountryNotFoundException;
import com.planetpulse.api.exception.indicator.FutureYearException;
import com.planetpulse.api.exception.indicator.IndicatorAlreadyExistsException;
import com.planetpulse.api.exception.indicator.NoIndicatorValueException;
import com.planetpulse.api.exception.targets.NotTargetRecordingException;
import com.planetpulse.api.exception.targets.TargetExistsException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---------- Targets ----------

    @ExceptionHandler(TargetExistsException.class)
    public ResponseEntity<Map<String, Object>> handleTargetExists(TargetExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)   // 409
                .body(Map.of("status", 409, "message", ex.getMessage()));
    }

    @ExceptionHandler(NotTargetRecordingException.class)
    public ResponseEntity<Map<String, Object>> handleNotTargetRecording(NotTargetRecordingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)   // 400
                .body(Map.of("status", 400, "message", ex.getMessage()));
    }


    // ---------- Indicators ----------

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

    @ExceptionHandler(FutureYearException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFutureYear(FutureYearException exception) {
        return new ErrorResponse(exception.getMessage());
    }

    // ---------- Communs (targets + indicators) ----------

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleIntegrity(DataIntegrityViolationException ex) {
        log.warn("Constraint violation : {}", ex.getMostSpecificCause().getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("status", 409,
                        "message", "Data incompatible with the database (non-existent country or duplicate)"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> details = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> details.putIfAbsent(fe.getField(), fe.getDefaultMessage()));
        return ResponseEntity.badRequest()
                .body(Map.of("status", 400, "message", "Invalid data", "details", details));
    }

    // ---------- Countries ----------

    @ExceptionHandler(CountriesAlreadyImportedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyImported(CountriesAlreadyImportedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ErrorResponse> handleExternalApi(ExternalApiException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorResponse(ex.getMessage()));
    }
}