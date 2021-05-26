package com.roboautomator.app.component.slider;

import com.jayway.jsonpath.JsonPath;
import com.roboautomator.app.component.slider.SliderEntity.SliderEntityBuilder;
import com.roboautomator.app.component.util.AbstractMockMvcTest;
import com.roboautomator.app.component.util.TestHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SliderController.class)
@AutoConfigureMockMvc
public class SliderControllerAdviceTest extends AbstractMockMvcTest {

    private static final String TEST_ENDPOINT = "/slider";

    private static final String TITLE = "test-title";
    private static final String COLOUR = "test-colour";

    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        var sliderController = new SliderController(sliderRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(sliderController).setControllerAdvice(SliderControllerAdvice.class)
                .build();
    }

    @Test
    void shouldReturn400WhenSliderIdIsNotValidUUID() throws Exception {

        var id = "invalid-UUID";

        var response = mockMvc
                .perform(get(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidSlider().build())))
                .andExpect(status().isBadRequest()).andReturn();

        verifyNoInteractions(sliderRepository);

        var responseAsString = response.getResponse().getContentAsString();
        System.out.println(responseAsString);
        assertThat(responseAsString).isNotNull();
        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("sliderId");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                .contains("invalid-UUID is not a valid UUID");

    }

    @Test
    void shouldReturn404WhenImageNotFound() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.empty()).given(sliderRepository).findById(id);

        var response = mockMvc
                .perform(get(TEST_ENDPOINT + "/" + id.toString()).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidSlider().build())))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Slider with id \"" + id + "\" not found");

    }

    private static SliderEntityBuilder<?, ?> createValidSlider() {
        return SliderEntity.builder().title(TITLE).colour(COLOUR);
    }

}
