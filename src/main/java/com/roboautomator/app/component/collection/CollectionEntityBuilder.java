package com.roboautomator.app.component.collection;

import java.util.Set;

import com.roboautomator.app.component.image.ImageEntity;
import com.roboautomator.app.component.slider.SliderEntity;
import com.roboautomator.app.component.util.DefaultEntityBuilder;

public class CollectionEntityBuilder extends DefaultEntityBuilder<CollectionEntityBuilder> {

    private String title;
    private Integer index;
    private String tagTitle;
    private String tagColour;
    private Integer titleImage;
    private Set<ImageEntity> images;
    private Set<SliderEntity> sliders;

    public CollectionEntityBuilder() {
        // EMPTY
    }

    public CollectionEntity build() {
        return new CollectionEntity(this);
    }

    public CollectionEntityBuilder title(String title) {
        this.title = title;
        return this;
    }

    public CollectionEntityBuilder index(Integer index) {
        this.index = index;
        return this;
    }

    public CollectionEntityBuilder tagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
        return this;
    }

    public CollectionEntityBuilder tagColour(String tagColour) {
        this.tagColour = tagColour;
        return this;
    }

    public CollectionEntityBuilder titleImage(Integer titleImage) {
        this.titleImage = titleImage;
        return this;
    }

    public CollectionEntityBuilder images(Set<ImageEntity> images) {
        this.images = images;
        return this;
    }

    public CollectionEntityBuilder sliders(Set<SliderEntity> sliders) {
        this.sliders = sliders;
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
