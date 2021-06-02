package com.roboautomator.app.component.image;

import java.util.Set;

import com.roboautomator.app.component.collection.CollectionEntity;
import com.roboautomator.app.component.util.DefaultEntityBuilder;

public class ImageEntityBuilder extends DefaultEntityBuilder <ImageEntityBuilder>{
    
    private String title;
    private String url;
    private Integer index;
    private String description;
    private Set <CollectionEntity> collections;

    public ImageEntityBuilder(){
        //EMPTY
    }

    public ImageEntity build() {
        return new ImageEntity(this);
    }

    public ImageEntityBuilder title (String title) {
        this.title = title;
        return this;
    }
    public ImageEntityBuilder url (String url){
        this.url = url;
        return this;
    }
    public ImageEntityBuilder index (Integer index){
        this.index = index;
        return this;
    }
    public ImageEntityBuilder description (String description) {
        this.description = description;
        return this;
    }
    public ImageEntityBuilder collections (Set <CollectionEntity> collections){
        this.collections = collections;
        return this;
    }

    public String getTitle() {
        return title;
    }
    public String getUrl() {
        return url;
    }
    public Integer getIndex() {
        return index;
    }
    public String getDescription() {
        return description;
    }
    public Set<CollectionEntity> getCollections() {
        return collections;
    }    

}
