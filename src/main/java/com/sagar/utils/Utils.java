package com.sagar.utils;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Utils {
    public static Map<String, String> buildErrorMap(BindingResult bindingResult, Locale locale, MessageSource messageSource) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = messageSource.getMessage(error, locale);
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
