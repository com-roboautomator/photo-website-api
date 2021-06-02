package com.roboautomator.app.component.slider;

public class SliderControllerValidationException extends RuntimeException{

    private final String field;

    public SliderControllerValidationException(String exceptionMessage, String field){
        super(exceptionMessage);
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
