package com.transport.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ErrorHandler {
    public static ErrorResponse handleValidationException(ConstraintViolationException e) {
        Map<String, String> fieldErrors = new HashMap<>();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            fieldErrors.put(fieldName, errorMessage);
        }

        return new ErrorResponse("Validation failed", fieldErrors);
    }

    @Getter
    public static class ErrorResponse {
        private String message;
        private Map<String, String> fieldErrors;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public ErrorResponse(String message, Map<String, String> fieldErrors) {
            this.message = message;
            this.fieldErrors = fieldErrors;
        }

    }
}