package com.roboautomator.app.component.slider;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.roboautomator.app.component.collection.CollectionEntity;
import com.roboautomator.app.component.util.DefaultEntity;
import com.roboautomator.app.component.util.StringHelper;

@Entity
@Table(name = "slider")
public class SliderEntity extends DefaultEntity {

    private String title;
    private String colour;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "collection_sliders", 
        joinColumns = @JoinColumn(name = "slider_id"), 
        inverseJoinColumns = @JoinColumn(name = "collection_id")
    )
    private Set<CollectionEntity> collections;

    public SliderEntity() {
        super(new SliderEntityBuilder());
    }

    public SliderEntity(SliderEntityBuilder builder){
        super(builder);
        this.title = builder.getTitle();
        this.colour = builder.getColour();
        this.collections = builder.getCollections();
    }

    public static SliderEntityBuilder builder () {
        return new SliderEntityBuilder();
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SliderEntity update (SliderUpdate sliderUpdate){
        collections = sliderUpdate.getCollections();
        title = sliderUpdate.getTitle();
        colour = sliderUpdate.getColour();
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
