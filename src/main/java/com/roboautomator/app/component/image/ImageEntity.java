package com.roboautomator.app.component.image;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roboautomator.app.component.collection.CollectionEntity;
import com.roboautomator.app.component.util.DefaultEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "image")
@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
public class ImageEntity extends DefaultEntity {
    
    private String title;
    private String url;
    private Integer index;
    private String description;
    
    @ManyToMany(mappedBy = "images")
    @JsonIgnore
    private Set <CollectionEntity> collections;

    public ImageEntity update(ImageUpdate imageUpdate){
        this.title = imageUpdate.getTitle();
        this.url = imageUpdate.getUrl();
        this.index = imageUpdate.getIndex();
        this.description = imageUpdate.getDescription();
        return this;
    }

}
