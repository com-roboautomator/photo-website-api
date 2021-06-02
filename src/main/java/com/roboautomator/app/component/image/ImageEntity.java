package com.roboautomator.app.component.image;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roboautomator.app.component.collection.CollectionEntity;
import com.roboautomator.app.component.util.DefaultEntity;
import com.roboautomator.app.component.util.DefaultEntityBuilder;

@Entity
@Table(name = "image")
public class ImageEntity extends DefaultEntity {

    private String title;
    private String url;
    private Integer index;
    private String description;

    @ManyToMany(mappedBy = "images")
    @JsonIgnore
    private Set<CollectionEntity> collections;

    public ImageEntity() {
        super(new DefaultEntityBuilder<ImageEntityBuilder>());
    }

    public ImageEntity(ImageEntityBuilder builder) {
        super(builder);
        this.title = builder.getTitle();
        this.url = builder.getUrl();
        this.index = builder.getIndex();
        this.description = builder.getDescription();
        this.collections = builder.getCollections();
    }

    public static ImageEntityBuilder builder() {
        return new ImageEntityBuilder();
    }

    public ImageEntity update(ImageUpdate imageUpdate) {
        this.title = imageUpdate.getTitle();
        this.url = imageUpdate.getUrl();
        this.index = imageUpdate.getIndex();
        this.description = imageUpdate.getDescription();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public Integer getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }

    public Set<CollectionEntity> getCollections() {
        return collections;
    }
}
