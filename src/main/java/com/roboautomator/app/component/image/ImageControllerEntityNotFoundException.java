package com.roboautomator.app.component.image;

import lombok.Getter;

@Getter
public class ImageControllerEntityNotFoundException extends RuntimeException {
    
    public ImageControllerEntityNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }

}
