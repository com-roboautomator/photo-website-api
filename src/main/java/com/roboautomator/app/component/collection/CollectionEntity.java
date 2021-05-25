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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "collection")
@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
public class CollectionEntity extends DefaultEntity {

    private String title;
    private Integer index;
    private String tagTitle;
    private String tagColour;
    private Integer titleImage;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name="image_collections",
        joinColumns = @JoinColumn(name="collection_id"),
        inverseJoinColumns = @JoinColumn(name="image_id")
    )
    private Set <ImageEntity> images;

    @ManyToMany(mappedBy = "collections")
    @JsonIgnore
    private Set <SliderEntity> sliders;
    
    public CollectionEntity update (CollectionUpdate collectionUpdate){
        title = collectionUpdate.getTitle();
        index = collectionUpdate.getIndex();
        tagTitle = collectionUpdate.getTagTitle();
        tagColour = collectionUpdate.getTagColour();
        images = collectionUpdate.getImages();
        titleImage = collectionUpdate.getTitleImage();
        return this;
    }

}