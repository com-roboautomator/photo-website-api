package com.roboautomator.app.component.collection;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roboautomator.app.component.image.ImageEntity;
import com.roboautomator.app.component.slider.SliderEntity;
import com.roboautomator.app.component.util.DefaultEntity;

@Entity
@Table(name = "collection")
public class CollectionEntity extends DefaultEntity {

    private String title;
    private Integer index;
    private String tagTitle;
    private String tagColour;
    private Integer titleImage;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "image_collections", joinColumns = @JoinColumn(name = "collection_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<ImageEntity> images;

    @ManyToMany(mappedBy = "collections")
    @JsonIgnore
    private Set<SliderEntity> sliders;

    public CollectionEntity() {
        super(new CollectionEntityBuilder());
    }

    public CollectionEntity(CollectionEntityBuilder builder) {
        super(builder);
        this.title = builder.getTitle();
        this.index = builder.getIndex();
        this.tagTitle = builder.getTagTitle();
        this.tagColour = builder.getTagColour();
        this.titleImage = builder.getTitleImage();
        this.images = builder.getImages();
        this.sliders = builder.getSliders();
    }

    public static CollectionEntityBuilder builder() {
        return new CollectionEntityBuilder();
    }

    public CollectionEntity update(CollectionUpdate collectionUpdate) {
        title = collectionUpdate.getTitle();
        index = collectionUpdate.getIndex();
        tagTitle = collectionUpdate.getTagTitle();
        tagColour = collectionUpdate.getTagColour();
        images = collectionUpdate.getImages();
        titleImage = collectionUpdate.getTitleImage();
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Integer getIndex() {
        return index;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public String getTagColour() {
        return tagColour;
    }

    public Integer getTitleImage() {
        return titleImage;
    }

    public Set<ImageEntity> getImages() {
        return images;
    }

    public Set<SliderEntity> getSliders() {
        return sliders;
    }

}