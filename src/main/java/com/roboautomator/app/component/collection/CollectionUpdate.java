package com.roboautomator.app.component.collection;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.roboautomator.app.component.image.ImageEntity;
import com.roboautomator.app.component.slider.SliderEntity;
import com.roboautomator.app.component.util.StringHelper;

public class CollectionUpdate {

    @NotBlank
    private String title;

    @NotNull
    private Integer index;

    private String tagTitle;
    private String tagColour;
    private Set<ImageEntity> images;
    private Set<SliderEntity> sliders;
    private Integer titleImage;

    public CollectionUpdate() {
        // EMPTY
    }

    public CollectionUpdate(CollectionUpdateBuilder builder) {
        this.title = builder.getTitle();
        this.index = builder.getIndex();
        this.tagTitle = builder.getTagTitle();
        this.tagColour = builder.getTagColour();
        this.images = builder.getImages();
        this.sliders = builder.getSliders();
        this.titleImage = builder.getTitleImage();
    }

    public static CollectionUpdateBuilder builder() {
        return new CollectionUpdateBuilder();
    }

    public CollectionEntity toCollectionEntity() {
        return CollectionEntity.builder().title(getTitle()).index(getIndex()).tagColour(getTagColour())
                .tagTitle(getTagTitle()).images(getImages()).sliders(getSliders()).titleImage(getTitleImage()).build();
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
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

    public Set<ImageEntity> getImages() {
        return images;
    }

    public Set<SliderEntity> getSliders() {
        return sliders;
    }

    public Integer getTitleImage() {
        return titleImage;
    }

}
