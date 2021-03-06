package com.roboautomator.app.component.slider;

import java.util.Set;

import com.roboautomator.app.component.collection.CollectionEntity;
import com.roboautomator.app.component.util.StringHelper;

public class SliderUpdate {

    private String title;
    private String colour;
    private Set<CollectionEntity> collections;

    public SliderUpdate() {
        // EMPTY
    }

    public SliderUpdate(SliderUpdateBuilder builder) {
        this.title = builder.getTitle();
        this.colour = builder.getColour();
        this.collections = builder.getCollections();
    }

    public static SliderUpdateBuilder builder() {
        return new SliderUpdateBuilder();
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SliderEntity toSliderEntity() {
        return SliderEntity.builder().collections(getCollections()).title(getTitle()).colour(getColour()).build();
    }

    public String getTitle() {
        return title;
    }

    public String getColour() {
        return colour;
    }

    public Set<CollectionEntity> getCollections() {
        return collections;
    }

}
