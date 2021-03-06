package com.roboautomator.app.component.image;

import com.roboautomator.app.component.util.StringHelper;

public class ImageUpdate {

    private String title;
    private String url;
    private Integer index;
    private String description;

    public ImageUpdate() {
        // EMPTY
    }

    public ImageUpdate(ImageUpdateBuilder builder) {
        this.title = builder.getTitle();
        this.url = builder.getUrl();
        this.index = builder.getIndex();
        this.description = builder.getDescription();
    }

    public static ImageUpdateBuilder builder () {
        return new ImageUpdateBuilder();
    }

    public ImageEntity toImageEntity() {
        return ImageEntity.builder().title(getTitle()).url(getUrl()).index(getIndex()).description(getDescription())
                .build();
    }

    public String toString() {
        try {
            return StringHelper.classToString(this);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
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
