package com.roboautomator.app.component.collection;

import com.roboautomator.app.component.image.ImageEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class CollectionUpdateTest {

    private static final String TITLE = "test-title";
    private static final Integer INDEX = 0;
    private static final Integer TITLE_IMAGE = 1;
    private static final String TAG_TITLE = "test-tag-title";
    private static final String TAG_COLOUR = "test-tag-colour";
    private static final Set <ImageEntity> IMAGES = Set.of(ImageEntity.builder().build());

    @Test
    void shouldBuildNewCollectionUpdateFromBuilder() {

        var collectionUpdate = CollectionUpdate.builder().title(TITLE).index(INDEX).titleImage(TITLE_IMAGE)
                .tagTitle(TAG_TITLE).tagColour(TAG_COLOUR).images(IMAGES).build();

        assertThat(collectionUpdate.getTitle()).isEqualTo(TITLE);
        assertThat(collectionUpdate.getIndex()).isEqualTo(INDEX);
        assertThat(collectionUpdate.getTitleImage()).isEqualTo(TITLE_IMAGE);
        assertThat(collectionUpdate.getTagTitle()).isEqualTo(TAG_TITLE);
        assertThat(collectionUpdate.getTagColour()).isEqualTo(TAG_COLOUR);
        assertThat(collectionUpdate.getImages()).isEqualTo(IMAGES);

    }

    @Test
    void shouldHaveAllValuesInToStringMethod(){
        var collectionUpdate = CollectionUpdate.builder().title(TITLE).index(INDEX).titleImage(TITLE_IMAGE)
        .tagTitle(TAG_TITLE).tagColour(TAG_COLOUR).images(IMAGES).build();

        assertThat(collectionUpdate.toString()).contains("title=" + TITLE);
        assertThat(collectionUpdate.toString()).contains("index=" + INDEX);
        assertThat(collectionUpdate.toString()).contains("titleImage=" + TITLE_IMAGE );
        assertThat(collectionUpdate.toString()).contains("tagTitle=" + TAG_TITLE);
        assertThat(collectionUpdate.toString()).contains("tagColour=" + TAG_COLOUR);
    }

    @Test
    void shouldBuildCollectionUpdate() {
        var collectionUpdateBuilder = CollectionUpdate.builder().title(TITLE).index(INDEX).titleImage(TITLE_IMAGE)
                .tagTitle(TAG_TITLE).images(IMAGES).tagColour(TAG_COLOUR);

        assertThat(collectionUpdateBuilder.toString()).contains("title=" + TITLE);
        assertThat(collectionUpdateBuilder.toString()).contains("index=" + INDEX);
        assertThat(collectionUpdateBuilder.toString()).contains("titleImage=" + TITLE_IMAGE);
        assertThat(collectionUpdateBuilder.toString()).contains("tagTitle=" + TAG_TITLE);
        assertThat(collectionUpdateBuilder.toString()).contains("tagColour=" + TAG_COLOUR);
        assertThat(collectionUpdateBuilder.build()).isNotNull();

    }

    @Test
    void shouldCreateCollectionEntityFromUpdate(){

        var collectionUpdate = CollectionUpdate.builder().title(TITLE).index(INDEX).titleImage(TITLE_IMAGE)
                .tagTitle(TAG_TITLE).tagColour(TAG_COLOUR).images(IMAGES).build();

        var collection = collectionUpdate.toCollectionEntity();

        assertThat(collection.getTitle()).isEqualTo(TITLE);
        assertThat(collection.getIndex()).isEqualTo(INDEX);
        assertThat(collection.getTitleImage()).isEqualTo(TITLE_IMAGE);
        assertThat(collection.getTagTitle()).isEqualTo(TAG_TITLE);
        assertThat(collection.getTagColour()).isEqualTo(TAG_COLOUR);
        assertThat(collection.getImages()).isEqualTo(IMAGES);

    }

}
