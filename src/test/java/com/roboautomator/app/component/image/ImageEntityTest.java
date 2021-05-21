package com.roboautomator.app.component.image;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class ImageEntityTest {

    private static final String TITLE = "test-title";
    private static final String URL = "test-url";
    private static final Integer INDEX = 0;
    private static final String DESCRIPTION = "test-description";

    @Test
    void shouldCreateNewEntityFromBuilder() {

        var uuid = UUID.randomUUID();
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var image = ImageEntity.builder().id(uuid).title(TITLE).url(URL).index(INDEX).description(DESCRIPTION)
                .createdAt(createdAt).updatedAt(updatedAt).build();

        assertThat(image.getId()).isEqualTo(uuid);
        assertThat(image.getTitle()).isEqualTo(TITLE);
        assertThat(image.getUrl()).isEqualTo(URL);
        assertThat(image.getIndex()).isEqualTo(INDEX);
        assertThat(image.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(image.getCreatedAt()).isEqualTo(createdAt);
        assertThat(image.getUpdatedAt()).isEqualTo(updatedAt);

    }

    @Test
    void shouldHaveAllValuesInToStringMethod() {

        var uuid = UUID.randomUUID();
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var image = ImageEntity.builder().id(uuid).title(TITLE).url(URL).index(INDEX).description(DESCRIPTION)
                .createdAt(createdAt).updatedAt(updatedAt).build();

        String imageString = image.toString();

        assertThat(imageString).contains("id=" + uuid);
        assertThat(imageString).contains("title=" + TITLE);
        assertThat(imageString).contains("url=" + URL);
        assertThat(imageString).contains("index=" + INDEX);
        assertThat(imageString).contains("description=" + DESCRIPTION);
        assertThat(imageString).contains("createdAt=" + createdAt);
        assertThat(imageString).contains("updatedAt=" + updatedAt);

    }

    @Test
    void shouldBuildImageEntity() {

        var uuid = UUID.randomUUID();

        var imageEntityBuilder = ImageEntity.builder().id(uuid).title(TITLE).url(URL).index(INDEX)
                .description(DESCRIPTION);

        var stringBuilder = imageEntityBuilder.toString();

        assertThat(stringBuilder).contains("id=" + uuid);
        assertThat(stringBuilder).contains("title=" + TITLE);
        assertThat(stringBuilder).contains("url=" + URL);
        assertThat(stringBuilder).contains("index=" + INDEX);
        assertThat(stringBuilder).contains("description=" + DESCRIPTION);

    }

    @Test
    void shouldUpdateImageEntity() {

        var uuid = UUID.randomUUID();
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var image = ImageEntity.builder().id(uuid).title(TITLE).url(URL).index(INDEX).description(DESCRIPTION)
                .createdAt(createdAt).updatedAt(updatedAt).build();

        assertThat(image.getId()).isEqualTo(uuid);
        assertThat(image.getTitle()).isEqualTo(TITLE);
        assertThat(image.getUrl()).isEqualTo(URL);
        assertThat(image.getIndex()).isEqualTo(INDEX);
        assertThat(image.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(image.getCreatedAt()).isEqualTo(createdAt);
        assertThat(image.getUpdatedAt()).isEqualTo(updatedAt);

        var imageUpdate = ImageUpdate.builder().title(TITLE + "-update").url(URL + "-update").index(INDEX + 1)
                .description(DESCRIPTION + "-update").build();

        image.update(imageUpdate);

        assertThat(image.getId()).isEqualTo(uuid);
        assertThat(image.getTitle()).isEqualTo(TITLE + "-update");
        assertThat(image.getUrl()).isEqualTo(URL + "-update");
        assertThat(image.getIndex()).isEqualTo(INDEX + 1);
        assertThat(image.getDescription()).isEqualTo(DESCRIPTION + "-update");
        assertThat(image.getCreatedAt()).isEqualTo(createdAt);
        assertThat(image.getUpdatedAt()).isEqualTo(updatedAt);
        
    }

}
