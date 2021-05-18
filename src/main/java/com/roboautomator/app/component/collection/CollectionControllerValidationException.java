package com.roboautomator.app.component.collection;

import lombok.Getter;

@Getter
public class CollectionControllerValidationException extends RuntimeException {

    private final String field;

    public CollectionControllerValidationException(String exceptionMessage, String field) {
        super(exceptionMessage);
        this.field = field;
    }

}
