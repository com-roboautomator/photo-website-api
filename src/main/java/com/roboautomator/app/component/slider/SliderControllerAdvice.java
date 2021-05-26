package com.roboautomator.app.component.slider;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.roboautomator.app.component.util.DefaultExceptionResponse;
import com.roboautomator.app.component.util.ValidationError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;

@ControllerAdvice
public class SliderControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ SliderControllerValidationException.class })
    public ResponseEntity<SliderExceptionResponse> handleSliderSliderValidationException(
            SliderControllerValidationException validationException) {
        var errors = List.of(new ValidationError(validationException.getField(), validationException.getMessage()));
        return ResponseEntity.badRequest().body(getValidationFailedResponse(errors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<SliderExceptionResponse> handleValidationException(MethodArgumentNotValidException ex) {

        var errors = new ArrayList<ValidationError>();

        ex.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ValidationError(fieldName, errorMessage));
        }));

        return ResponseEntity.badRequest().body(getValidationFailedResponse(errors));

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ SliderControllerEntityNotFoundException.class })
    public ResponseEntity<DefaultExceptionResponse<String>> handleEntityNotFoundException(
            SliderControllerEntityNotFoundException ex) {

        DefaultExceptionResponse<String> response = new DefaultExceptionResponse<>(ex.getMessage(),
                Collections.emptyList());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    private SliderExceptionResponse getValidationFailedResponse(List<ValidationError> errors) {
        return SliderExceptionResponse.builder().message("Validation failed").errors(errors).build();
    }

}
