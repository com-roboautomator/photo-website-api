package com.roboautomator.app.component.util;

import java.time.OffsetDateTime;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class DefaultEntityBuilder<T extends DefaultEntityBuilder<T>> {

    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public DefaultEntityBuilder() {
        // EMPTY
    }

    public DefaultEntity build() {
        return new DefaultEntity(this);
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public T id(UUID id) {
        this.id = id;
        return (T) this;
    }

    public T createdAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return (T) this;
    }

    public T updatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return (T) this;
    }

    public UUID getId() {
        return id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

}
