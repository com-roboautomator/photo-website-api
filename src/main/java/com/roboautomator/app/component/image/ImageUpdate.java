package com.roboautomator.app.component.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ImageUpdate {
    
    private String title;
    private String url;
    private Integer index;
    private String description;

    private ImageEntity toImageEntity(){
        return ImageEntity.builder()
            .title(getTitle())
            .url(getUrl())
            .index(getIndex())
            .description(getDescription())
            .build();
    }

}
