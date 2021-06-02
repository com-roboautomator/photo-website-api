package com.roboautomator.app.component.slider;

import java.util.Set;

import com.roboautomator.app.component.collection.CollectionEntity;

public class SliderUpdateBuilder {

    private String title;
    private String colour;
    private Set<CollectionEntity> collections;

    public SliderUpdateBuilder() {
        // EMPTY
    }

    public SliderUpdate build() {
        return new SliderUpdate(this);
    }

    public SliderUpdateBuilder title(String title) {
        this.title = title;
        return this;
    }

    public SliderUpdateBuilder colour(String colour) {
        this.colour = colour;
        return this;
    }

    public SliderUpdateBuilder collections(Set<CollectionEntity> collections) {
        this.collections = collections;
        return this;
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
