package com.roboautomator.app.component.collection;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

import com.roboautomator.app.component.image.ImageEntity;

import org.junit.jupiter.api.Test;

public class CollectionEntityTest {

    private static final String TITLE = "test-title";
    private static final Integer INDEX = 0;
    private static final Integer TITLE_IMAGE = 1;
    private static final String TAG_TITLE = "test-tag-title";
    private static final String TAG_COLOUR = "test-tag-colour";
    private static final Set <ImageEntity> IMAGES = Set.of(ImageEntity.builder().build());

    @Test
    void shouldBuildNewEntityFromBuilder() {
        var testId = UUID.randomUUID();
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var collectionEntity = CollectionEntity.builder().id(testId).title(TITLE).index(INDEX).titleImage(TITLE_IMAGE)
                .tagTitle(TAG_TITLE).tagColour(TAG_COLOUR).images(IMAGES).createdAt(createdAt).updatedAt(updatedAt)
                .build();

        assertThat(collectionEntity.getId()).isEqualTo(testId);
        assertThat(collectionEntity.getTitle()).isEqualTo(TITLE);
        assertThat(collectionEntity.getIndex()).isEqualTo(INDEX);
        assertThat(collectionEntity.getTitleImage()).isEqualTo(TITLE_IMAGE);
        assertThat(collectionEntity.getTagTitle()).isEqualTo(TAG_TITLE);
        assertThat(collectionEntity.getTagColour()).isEqualTo(TAG_COLOUR);
        assertThat(collectionEntity.getImages()).isEqualTo(IMAGES);
        assertThat(collectionEntity.getCreatedAt()).isEqualTo(createdAt);
        assertThat(collectionEntity.getUpdatedAt()).isEqualTo(updatedAt);

    }

    @Test
    void shouldHaveAllValuesInToStringMethod() {

        var testId = UUID.randomUUID();

        var collectionEntity = CollectionEntity.builder().id(testId).title(TITLE).index(INDEX).titleImage(TITLE_IMAGE)
                .tagTitle(TAG_TITLE).tagColour(TAG_COLOUR).build();

        assertThat(collectionEntity.toString()).contains("id=" + testId);
        assertThat(collectionEntity.toString()).contains("title=" + TITLE);
        assertThat(collectionEntity.toString()).contains("index=" + INDEX);
        assertThat(collectionEntity.toString()).contains("titleImage=" + TITLE_IMAGE);
        assertThat(collectionEntity.toString()).contains("tagTitle=" + TAG_TITLE);
        assertThat(collectionEntity.toString()).contains("tagColour=" + TAG_COLOUR);

    }

    @Test
    void shouldBuildCollectionEntity() {

        var testId = UUID.randomUUID();
        var collectionEntityBuilder = CollectionEntity.builder().id(testId).title(TITLE).index(INDEX)
                .titleImage(TITLE_IMAGE).tagTitle(TAG_TITLE).tagColour(TAG_COLOUR);

        assertThat(collectionEntityBuilder.toString()).contains("id=" + testId);
        assertThat(collectionEntityBuilder.toString()).contains("title=" + TITLE);
        assertThat(collectionEntityBuilder.toString()).contains("index=" + INDEX);
        assertThat(collectionEntityBuilder.toString()).contains("titleImage=" + TITLE_IMAGE);
        assertThat(collectionEntityBuilder.toString()).contains("tagTitle=" + TAG_TITLE);
        assertThat(collectionEntityBuilder.toString()).contains("tagColour=" + TAG_COLOUR);
        assertThat(collectionEntityBuilder.build()).isNotNull();

    }

    @Test
    void shouldUpdateCollectionEntityFromCollectionUpdate() {

        var testId = UUID.randomUUID();

        var collectionEntity = CollectionEntity.builder().id(testId).title(TITLE).index(INDEX).titleImage(TITLE_IMAGE)
                .tagTitle(TAG_TITLE).tagColour(TAG_COLOUR).images(IMAGES).build();

        assertThat(collectionEntity.getId()).isEqualTo(testId);
        assertThat(collectionEntity.getTitle()).isEqualTo(TITLE);
        assertThat(collectionEntity.getIndex()).isEqualTo(INDEX);
        assertThat(collectionEntity.getTitleImage()).isEqualTo(TITLE_IMAGE);
        assertThat(collectionEntity.getTagTitle()).isEqualTo(TAG_TITLE);
        assertThat(collectionEntity.getTagColour()).isEqualTo(TAG_COLOUR);
        assertThat(collectionEntity.getImages()).isEqualTo(IMAGES);

        var collectionUpdate = CollectionUpdate.builder().title(TITLE + "-update").index(INDEX + 1)
                .titleImage(TITLE_IMAGE + 1).tagTitle(TAG_TITLE + "-update").tagColour(TAG_COLOUR + "-update")
                .images(IMAGES).build();

        collectionEntity.update(collectionUpdate);

        assertThat(collectionEntity.getId()).isEqualTo(testId);
        assertThat(collectionEntity.getTitle()).isEqualTo(TITLE + "-update");
        assertThat(collectionEntity.getIndex()).isEqualTo(INDEX + 1);
        assertThat(collectionEntity.getTitleImage()).isEqualTo(TITLE_IMAGE + 1);
        assertThat(collectionEntity.getTagTitle()).isEqualTo(TAG_TITLE + "-update");
        assertThat(collectionEntity.getTagColour()).isEqualTo(TAG_COLOUR + "-update");
        assertThat(collectionEntity.getImages()).isEqualTo(IMAGES);

    }

}
