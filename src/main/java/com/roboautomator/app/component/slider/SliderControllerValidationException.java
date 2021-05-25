package com.roboautomator.app.component.slider;

import lombok.Getter;

@Getter
public class SliderControllerValidationException extends RuntimeException{

    private final String field;

    public SliderControllerValidationException(String exceptionMessage, String field){
        super(exceptionMessage);
        this.field = field;
    }

}
