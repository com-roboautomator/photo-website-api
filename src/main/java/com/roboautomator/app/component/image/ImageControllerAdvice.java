package com.roboautomator.app.component.image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.roboautomator.app.component.collection.CollectionControllerEntityNotFoundException;
import com.roboautomator.app.component.util.DefaultExceptionResponse;
import com.roboautomator.app.component.util.ValidationError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ImageControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ImageControllerValidationException.class })
    public ResponseEntity<ImageExceptionResponse> handleImageControllerValidationException(
            ImageControllerValidationException validationException) {
        var errors = List.of(new ValidationError(validationException.getField(), validationException.getMessage()));
        return ResponseEntity.badRequest().body(getValidationFailedResponse(errors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class})
    public ResponseEntity<ImageExceptionResponse> handleValidationException(MethodArgumentNotValidException ex){

        var errors = new ArrayList<ValidationError>();
        
        ex.getBindingResult().getAllErrors().forEach((error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ValidationError(fieldName, errorMessage));
        }));

        return ResponseEntity.badRequest().body(getValidationFailedResponse(errors));

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ ImageControllerEntityNotFoundException.class })
    public ResponseEntity<DefaultExceptionResponse<String>> handleEntityNotFoundException(
            ImageControllerEntityNotFoundException ex) {

        DefaultExceptionResponse<String> response = new DefaultExceptionResponse<>(ex.getMessage(),
                Collections.emptyList());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }

    private ImageExceptionResponse getValidationFailedResponse(List<ValidationError> errors) {
        return ImageExceptionResponse.builder().message("Validation failed").errors(errors).build();
    }

}
