package com.roboautomator.app.component.slider;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SliderUpdateTest {
    
    private static final String TITLE = "test-title";
    private static final String COLOUR = "test-colour";

    @Test
    void shouldCreateSliderUpdateFromBuilder() {
        
        var sliderUpdate = SliderUpdate.builder()
            .title(TITLE)
            .colour(COLOUR)
            .build();

        assertThat(sliderUpdate.getTitle()).isEqualTo(TITLE);
        assertThat(sliderUpdate.getColour()).isEqualTo(COLOUR);

    }

    @Test
    void shouldHaveAllValuesInToStringMethod(){

        var sliderUpdate = SliderUpdate.builder()
            .title(TITLE)
            .colour(COLOUR)
            .build();

        String sliderToString = sliderUpdate.toString();

        assertThat(sliderToString).contains("title=" + TITLE);
        assertThat(sliderToString).contains("colour=" + COLOUR);

    }

    @Test
    void shouldBuildSliderUpdate(){

        var sliderUpdate = SliderUpdate.builder()
            .title(TITLE)
            .colour(COLOUR)
            .build();

        String updateString = sliderUpdate.toString();

        assertThat(updateString).contains("title=" + TITLE);
        assertThat(updateString).contains("colour=" + COLOUR);

    }

    @Test
    void shouldCreateSliderEntityFromUpdate(){

        var sliderUpdate = SliderUpdate.builder()
        .title(TITLE)
        .colour(COLOUR)
        .build();

        var sliderEntity = sliderUpdate.toSliderEntity();

        assertThat(sliderEntity.getTitle()).isEqualTo(TITLE);
        assertThat(sliderEntity.getColour()).isEqualTo(COLOUR);

    }

}
