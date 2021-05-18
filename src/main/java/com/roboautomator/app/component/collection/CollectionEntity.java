package com.roboautomator.app.component.collection;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.roboautomator.app.component.image.ImageEntity;
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
    private ImageEntity images[];
    private Integer titleImage;

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