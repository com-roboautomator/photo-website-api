package com.roboautomator.app.component.util;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@MappedSuperclass
public class DefaultEntity {
    
    @Id
    private UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    public DefaultEntity(DefaultEntityBuilder<?> builder) {
        this.id = builder.getId();
        this.createdAt = builder.getCreatedAt();
        this.updatedAt = builder.getUpdatedAt();
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
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
