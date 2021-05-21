package com.roboautomator.app.component.collection;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.roboautomator.app.component.image.ImageEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CollectionUpdate {

    @NotBlank
    private String title;

    @NotNull
    private Integer index;

    private String tagTitle;
    private String tagColour;
    private Set <ImageEntity> images;
    private Integer titleImage;

    public CollectionEntity toCollectionEntity() {
        return CollectionEntity.builder()
            .title(getTitle())
            .index(getIndex())
            .tagColour(getTagColour())
            .tagTitle(getTagTitle())
            .images(getImages())
            .titleImage(getTitleImage())
            .build();
    }

}
