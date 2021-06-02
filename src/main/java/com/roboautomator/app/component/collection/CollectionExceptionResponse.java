package com.roboautomator.app.component.collection;

import java.util.List;

import com.roboautomator.app.component.util.DefaultExceptionResponse;
import com.roboautomator.app.component.util.ValidationError;

public class CollectionExceptionResponse extends DefaultExceptionResponse<ValidationError> {

    public CollectionExceptionResponse(String message, List<ValidationError> errors) {
        super(message, errors);
    }
}
