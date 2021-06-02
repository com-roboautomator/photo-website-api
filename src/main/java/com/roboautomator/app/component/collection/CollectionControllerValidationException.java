package com.roboautomator.app.component.collection;

public class CollectionControllerValidationException extends RuntimeException {

    private final String field;

    public CollectionControllerValidationException(String exceptionMessage, String field) {
        super(exceptionMessage);
        this.field = field;
    }

    public String getField() {
        return field;
    }

}
