package com.roboautomator.app.component.image;

import lombok.Getter;

@Getter
public class ImageControllerValidationException extends RuntimeException {

    private final String field;

    public ImageControllerValidationException (String exceptionMessage, String field){
        super(exceptionMessage);
        this.field = field;
    }
}
