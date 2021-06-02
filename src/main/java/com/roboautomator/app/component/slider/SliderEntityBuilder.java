package com.roboautomator.app.component.slider;

import java.util.Set;

import com.roboautomator.app.component.collection.CollectionEntity;
import com.roboautomator.app.component.util.DefaultEntityBuilder;
import com.roboautomator.app.component.util.StringHelper;

public class SliderEntityBuilder extends DefaultEntityBuilder<SliderEntityBuilder> {

    private String title;
    private String colour;
    private Set<CollectionEntity> collections;

    public SliderEntityBuilder() {
        // EMPTY
    }

    public SliderEntity build() {
        return new SliderEntity(this);
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SliderEntityBuilder title(String title) {
        this.title = title;
        return this;
    }

    public SliderEntityBuilder colour(String colour) {
        this.colour = colour;
        return this;
    }

    public SliderEntityBuilder collections(Set<CollectionEntity> collections){
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
