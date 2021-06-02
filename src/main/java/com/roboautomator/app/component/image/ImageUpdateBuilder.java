package com.roboautomator.app.component.image;

import com.roboautomator.app.component.util.StringHelper;

public class ImageUpdateBuilder {

    private String title;
    private String url;
    private Integer index;
    private String description;

    public ImageUpdateBuilder() {
        // EMPTY
    }

    public ImageUpdate build() {
        return new ImageUpdate(this);
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ImageUpdateBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ImageUpdateBuilder url(String url) {
        this.url = url;
        return this;
    }

    public ImageUpdateBuilder index(Integer index) {
        this.index = index;
        return this;
    }

    public ImageUpdateBuilder description(String description) {
        this.description = description;
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

}
