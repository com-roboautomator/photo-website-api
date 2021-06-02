package com.roboautomator.app.component.collection;

import java.util.Set;

import com.roboautomator.app.component.image.ImageEntity;
import com.roboautomator.app.component.slider.SliderEntity;
import com.roboautomator.app.component.util.StringHelper;

public class CollectionUpdateBuilder {

    private String title;
    private Integer index;
    private String tagTitle;
    private String tagColour;
    private Set <ImageEntity> images;
    private Set <SliderEntity> sliders;
    private Integer titleImage;

    public CollectionUpdateBuilder() {
        //EMPTY
    }

    public CollectionUpdate build() {
        return new CollectionUpdate(this);
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CollectionUpdateBuilder title (String title) {
        this.title = title;
        return this;
    }

    public CollectionUpdateBuilder index (Integer index) {
        this.index = index;
        return this;
    }
    
    public CollectionUpdateBuilder tagTitle (String tagTitle) {
        this.tagTitle = tagTitle;
        return this;
    }

    public CollectionUpdateBuilder tagColour (String tagColour) {
        this.tagColour = tagColour;
        return this;
    }

    public CollectionUpdateBuilder images (Set<ImageEntity> images){
        this.images = images;
        return this;
    }

    public CollectionUpdateBuilder sliders (Set<SliderEntity> sliders){
        this.sliders = sliders;
        return this;
    }

    public CollectionUpdateBuilder titleImage (Integer titleImage){
        this.titleImage = titleImage;
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
