package com.roboautomator.app.component.slider;

import lombok.Getter;

@Getter
public class SliderControllerEntityNotFoundException extends RuntimeException {

    public SliderControllerEntityNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }

}
