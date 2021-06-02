package com.roboautomator.app.component.slider;

import java.util.List;

import com.roboautomator.app.component.util.DefaultExceptionResponse;
import com.roboautomator.app.component.util.ValidationError;

public class SliderExceptionResponse extends DefaultExceptionResponse<ValidationError> {
    public SliderExceptionResponse(String message, List<ValidationError> errors) {
        super(message, errors);
    }
}
