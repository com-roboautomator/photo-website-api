package com.roboautomator.app.component.slider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import com.roboautomator.app.component.slider.SliderEntity.SliderEntityBuilder;
import com.roboautomator.app.component.util.AbstractMockMvcTest;
import com.roboautomator.app.component.util.TestHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@AutoConfigureMockMvc
@WebMvcTest(SliderRepository.class)
public class SliderControllerTest extends AbstractMockMvcTest {

    private static final String TEST_ENDPOINT = "/slider";
    private static final String TITLE = "test-title";
    private static final String COLOUR = "test-colour";

    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() throws JsonProcessingException {
        var sliderController = new SliderController(sliderRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(sliderController).setControllerAdvice(SliderControllerAdvice.class)
                .build();
    }

    @Test
    void shouldReturn400WhenSliderIsNotValidUUIDForUpdates() throws Exception {
        var id = "invalid-UUID";

        var response = mockMvc
                .perform(put(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidSlider().build())))
                .andExpect(status().isBadRequest()).andReturn();

        verifyNoInteractions(sliderRepository);

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotNull();
        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("sliderId");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                .contains("invalid-UUID is not a valid UUID");

    }

    @Test
    void shouldReturn404NotFoundWhenSliderDoesNotExistOnUpdate() throws Exception {
        var id = UUID.randomUUID();

        willReturn(Optional.empty()).given(sliderRepository).findById(id);

        var response = mockMvc
                .perform(put(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidSlider().build())))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Slider with id \"" + id + "\" not found");

    }

    @Test
    void shouldReturn200OKWhenUpdatingSliderEntity() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.of(createValidSlider().title(TITLE + "-previous").colour(COLOUR + "-previous").build()))
                .given(sliderRepository).findById(id);

        var response = mockMvc
                .perform(put(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidSliderUpdate().build())))
                .andExpect(status().isOk()).andReturn();

        var unpackedResponse = response.getResponse().getContentAsString();

        assertThat(unpackedResponse).isNotEmpty();
        assertThat(unpackedResponse).doesNotContain("previous-title");
        assertThat(unpackedResponse).doesNotContain("previous-colour");

        assertThat(unpackedResponse).contains(TITLE);
        assertThat(unpackedResponse).contains(COLOUR);

    }

    @Test
    void shouldReturn400WhenSliderIsNotValidUUIDForGet() throws Exception {
        var id = UUID.randomUUID();

        willReturn(Optional.empty()).given(sliderRepository).findById(id);

        var response = mockMvc.perform(get(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Slider with id \"" + id + "\" not found");
    }

    @Test
    void shouldReturn404NotFoundWhenSliderDoesNotExistOnGet() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.empty()).given(sliderRepository).findById(id);

        var response = mockMvc.perform(get(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Slider with id \"" + id + "\" not found");

    }

    @Test
    void shouldReturn200OKWhenGettingSliderEntity() throws Exception {

        var id = "invalid-UUID";

        var response = mockMvc
                .perform(get(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidSlider().build())))
                .andExpect(status().isBadRequest()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotNull();
        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("sliderId");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                .contains("invalid-UUID is not a valid UUID");

    }

    @Test
    void shouldReturn200OKWhenGettingAllSliders() throws Exception {

        var id1 = UUID.randomUUID();
        var id2 = UUID.randomUUID();

        willReturn(List.of(SliderEntity.builder().id(id1).build(), SliderEntity.builder().id(id2).build()))
                .given(sliderRepository).findAll();

        var response = mockMvc.perform(get(TEST_ENDPOINT)).andExpect(status().isOk()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();
        var responseAsArray = TestHelper.parseJsonArray(responseAsString);

        assertThat(responseAsArray.length()).isEqualTo(2);

        var response1 = responseAsArray.getJSONObject(0);
        var response2 = responseAsArray.getJSONObject(1);

        assertThat(response1.get("id").toString()).contains(id1.toString());
        assertThat(response2.get("id").toString()).contains(id2.toString());
    }

    @Test
    void shouldReturn200OKWhenCreatingSlider() throws Exception {

        willReturn(createValidSlider().build()).given(sliderRepository).save(any());

        mockMvc.perform(post(TEST_ENDPOINT + "/").contentType(MediaType.APPLICATION_JSON)
                .content(TestHelper.serializeObject(createValidSlider().build()))).andExpect(status().isOk())
                .andReturn();

        var argumentCapture = ArgumentCaptor.forClass(SliderEntity.class);
        verify(sliderRepository).save(argumentCapture.capture());

        var sliderEntity = argumentCapture.getValue();
        assertThat(sliderEntity.getId()).isNotNull();
    }

    @Test
    void shouldReturn400WhenSliderIsNotValidUUIDForDelete() throws Exception {

            var id = "invalid-UUID";

            var response = mockMvc
                            .perform(delete(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                                            .content(TestHelper.serializeObject(createValidSlider().build())))
                            .andExpect(status().isBadRequest()).andReturn();

            var responseAsString = response.getResponse().getContentAsString();

            assertThat(responseAsString).isNotNull();
            assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
            assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("sliderId");
            assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                    .contains("invalid-UUID is not a valid UUID");

    }

    @Test
    void shouldReturn404WhenImageIsNotFoundForDelete() throws Exception {

            var id = UUID.randomUUID();

            willReturn(Optional.empty()).given(sliderRepository).findById(id);

            var response = mockMvc
                            .perform(delete(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                                            .content(TestHelper.serializeObject(createValidSlider().build())))
                            .andExpect(status().isNotFound()).andReturn();

            var responseAsString = response.getResponse().getContentAsString();

            assertThat(responseAsString).isNotEmpty();
            assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                            .isEqualTo("Slider with id \"" + id + "\" not found");

    }

    @Test
    void shouldReturn200OKWhenDeletingSlider() throws Exception {

            var id = UUID.randomUUID();

            willReturn(Optional.of(createValidSlider().id(id).build())).given(sliderRepository).findById(id);

            mockMvc.perform(delete(TEST_ENDPOINT + "/" + id)).andExpect(status().isOk());

            verify(sliderRepository, times(1)).deleteById(eq(id));

    }

    private static SliderEntityBuilder<?, ?> createValidSlider() {
        return SliderEntity.builder().title(TITLE).colour(COLOUR);
    }

    private static SliderUpdate.SliderUpdateBuilder createValidSliderUpdate() {
        return SliderUpdate.builder().title(TITLE).colour(COLOUR);
    }

}
