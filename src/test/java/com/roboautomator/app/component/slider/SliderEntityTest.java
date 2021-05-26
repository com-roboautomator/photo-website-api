package com.roboautomator.app.component.slider;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

public class SliderEntityTest {

    private static final String TITLE = "test-title";
    private static final String COLOUR = "test-colour";

    @Test
    void shouldCreateNewEntityFromBuilder() {
        var uuid = UUID.randomUUID();
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var slider = SliderEntity.builder().id(uuid).title(TITLE).colour(COLOUR).createdAt(createdAt)
                .updatedAt(updatedAt).build();

        assertThat(slider.getId()).isEqualTo(uuid);
        assertThat(slider.getTitle()).isEqualTo(TITLE);
        assertThat(slider.getColour()).isEqualTo(COLOUR);
        assertThat(slider.getCreatedAt()).isEqualTo(createdAt);
        assertThat(slider.getUpdatedAt()).isEqualTo(updatedAt);

    }

    @Test
    void shouldHaveAllValuesInToStringMethod(){

        var uuid = UUID.randomUUID();
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var slider = SliderEntity.builder().id(uuid).title(TITLE).colour(COLOUR).createdAt(createdAt)
        .updatedAt(updatedAt).build();

        String sliderString = slider.toString();

        assertThat(sliderString).contains("id=" + uuid);
        assertThat(sliderString).contains("title=" + TITLE);
        assertThat(sliderString).contains("colour=" + COLOUR);
        assertThat(sliderString).contains("updatedAt=" + updatedAt);
        assertThat(sliderString).contains("createdAt=" + createdAt);

    }

    @Test
    void shouldBuildImageEntity() {

        var uuid = UUID.randomUUID();

        var sliderEntityBuilder = SliderEntity.builder().id(uuid).title(TITLE).colour(COLOUR);

        var builderString = sliderEntityBuilder.toString();

        assertThat(builderString).contains("id=" + uuid);
        assertThat(builderString).contains("title=" + TITLE);
        assertThat(builderString).contains("colour=" + COLOUR);

    }   

    @Test
    void shouldUpdateSliderEntity() {

        var uuid = UUID.randomUUID();
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var slider = SliderEntity.builder().id(uuid).title(TITLE).colour(COLOUR).createdAt(createdAt)
        .updatedAt(updatedAt).build();

        var sliderUpdate = SliderUpdate.builder().title(TITLE + "-update").colour(COLOUR + "-update").build();

        slider.update(sliderUpdate);

        assertThat(slider.getTitle()).isNotEqualTo(TITLE);
        assertThat(slider.getColour()).isNotEqualTo(COLOUR);

        assertThat(slider.getId()).isEqualTo(uuid);
        assertThat(slider.getTitle()).isEqualTo(TITLE + "-update");
        assertThat(slider.getColour()).isEqualTo(COLOUR + "-update");
        assertThat(slider.getCreatedAt()).isEqualTo(createdAt);
        assertThat(slider.getUpdatedAt()).isEqualTo(updatedAt);

    }

}