package com.roboautomator.app.component.collection;

import lombok.Getter;

@Getter
public class CollectionControllerEntityNotFoundException extends RuntimeException {
    
    public CollectionControllerEntityNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }

}
