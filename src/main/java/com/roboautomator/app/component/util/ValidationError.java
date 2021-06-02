package com.roboautomator.app.component.util;

public class ValidationError {
    private final String field;
    private final String error;

    public ValidationError(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}
