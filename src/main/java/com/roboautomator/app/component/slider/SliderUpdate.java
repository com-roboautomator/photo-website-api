package com.roboautomator.app.component.slider;

import java.util.Set;

import com.roboautomator.app.component.collection.CollectionEntity;

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
public class SliderUpdate {

    private String title;
    private String colour;

    private Set<CollectionEntity> collections;

    public SliderEntity toSliderEntity() {
        return SliderEntity.builder()
            .collections(getCollections())
            .title(getTitle())
            .colour(getColour())
            .build();
    }

}
