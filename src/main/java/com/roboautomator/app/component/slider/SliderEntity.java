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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "slider")
@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
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

    public SliderEntity update (SliderUpdate sliderUpdate){
        collections = sliderUpdate.getCollections();
        title = sliderUpdate.getTitle();
        colour = sliderUpdate.getColour();
        return this;
    }

}
