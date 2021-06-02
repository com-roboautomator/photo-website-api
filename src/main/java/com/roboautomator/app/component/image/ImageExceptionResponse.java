package com.roboautomator.app.component.image;

import java.util.List;

import com.roboautomator.app.component.util.DefaultExceptionResponse;
import com.roboautomator.app.component.util.ValidationError;

public class ImageExceptionResponse extends DefaultExceptionResponse<ValidationError> {

    public ImageExceptionResponse(String message, List<ValidationError> errors) {
        super(message, errors);
    }
}
