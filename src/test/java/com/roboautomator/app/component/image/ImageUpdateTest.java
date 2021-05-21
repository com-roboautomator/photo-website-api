package com.roboautomator.app.component.image;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageUpdateTest {
    
    private static final String TITLE = "test-title";
    private static final String URL = "test-url";
    private static final Integer INDEX = 0;
    private static final String DESCRIPTION = "test-description";

    @Test
    void shouldCreateImageUpdateFromBuilder() {

        var imageUpdate = ImageUpdate.builder()
            .title(TITLE)
            .url(URL)
            .index(INDEX)
            .description(DESCRIPTION)
            .build();

        assertThat(imageUpdate.getTitle()).isEqualTo(TITLE);
        assertThat(imageUpdate.getUrl()).isEqualTo(URL);
        assertThat(imageUpdate.getIndex()).isZero();
        assertThat(imageUpdate.getDescription()).isEqualTo(DESCRIPTION);

    }

    @Test
    void shouldHaveAllValuesInToStringMethod(){

        var imageUpdate = ImageUpdate.builder()
        .title(TITLE)
        .url(URL)
        .index(INDEX)
        .description(DESCRIPTION)
        .build();

        var imageUpdateString = imageUpdate.toString();

        assertThat(imageUpdateString).contains("title=" + TITLE);
        assertThat(imageUpdateString).contains("url=" + URL);
        assertThat(imageUpdateString).contains("index=" + INDEX);
        assertThat(imageUpdateString).contains("description=" + DESCRIPTION);

    }

    @Test
    void shouldBuildImageUpdate(){

        var imageUpdate = ImageUpdate.builder()
            .title(TITLE)
            .url(URL)
            .index(INDEX)
            .description(DESCRIPTION);

            var imageUpdateBuilderString = imageUpdate.toString();

            assertThat(imageUpdateBuilderString).contains("title=" + TITLE);
            assertThat(imageUpdateBuilderString).contains("url=" + URL);
            assertThat(imageUpdateBuilderString).contains("index=" + INDEX);
            assertThat(imageUpdateBuilderString).contains("description=" + DESCRIPTION);

    }

    @Test
    void shouldCreateImageEntityFromUpdate(){

        var imageUpdate = ImageUpdate.builder()
        .title(TITLE)
        .url(URL)
        .index(INDEX)
        .description(DESCRIPTION)
        .build();

        var imageEntity = imageUpdate.toImageEntity();

        assertThat(imageEntity.getTitle()).isEqualTo(TITLE);
        assertThat(imageEntity.getUrl()).isEqualTo(URL);
        assertThat(imageEntity.getIndex()).isZero();
        assertThat(imageEntity.getDescription()).isEqualTo(DESCRIPTION);
        

    }


}
