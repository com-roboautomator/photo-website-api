package com.roboautomator.app.component.image;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.roboautomator.app.component.image.ImageEntity.ImageEntityBuilder;
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
@WebMvcTest(ImageRepository.class)
public class ImageControllerTest extends AbstractMockMvcTest {

    private static final String TEST_ENDPOINT = "/image";
    private static final String TEST_TITLE = "test-title";
    private static final String TEST_URL = "test-url";
    private static final Integer TEST_INDEX = 0;
    private static final String TEST_DESCRIPTION = "test-description";

    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() throws JsonProcessingException {
        var imageController = new ImageController(imageRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).setControllerAdvice(ImageControllerAdvice.class)
                .build();
    }

    @Test
    void shouldReturn400WhenImageIdIsNotValidUUIDForUpdates() throws Exception {

        var id = "invalid-UUID";

        var response = mockMvc
                .perform(put(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidImageBuilder().build())))
                .andExpect(status().isBadRequest()).andReturn();

        verifyNoInteractions(imageRepository);

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotNull();
        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("imageId");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                .contains("invalid-UUID is not a valid UUID");
    }

    @Test
    void shouldReturn404NotFoundWhenImageDoesNotExistOnUpdate() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.empty()).given(imageRepository).findById(id);

        var response = mockMvc
                .perform(put(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidImage().build())))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Image with id \"" + id + "\" not found");

    }

    @Test
    void shouldReturn200OKWhenUpdatingImageEntity() throws JsonProcessingException, Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.of(createValidImage().description("previous-description").url("previous-url")
                .title("previous-title").id(id).build())).given(imageRepository).findById(id);

        var response = mockMvc
                .perform(put(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidImageBuilder().build())))
                .andExpect(status().isOk()).andReturn();

        var unpackedResponse = response.getResponse().getContentAsString();

        System.out.println("RESPONSE " + unpackedResponse);

        assertThat(unpackedResponse).isNotEmpty();
        assertThat(unpackedResponse).doesNotContain("previous-title");
        assertThat(unpackedResponse).doesNotContain("previous-description");
        assertThat(unpackedResponse).doesNotContain("previous-url");

        assertThat(unpackedResponse).contains(TEST_TITLE);
        assertThat(unpackedResponse).contains(TEST_DESCRIPTION);
        assertThat(unpackedResponse).contains(TEST_URL);

    }

    @Test
    void shouldReturn400WhenImageIsNotValidUUIDForGet() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.empty()).given(imageRepository).findById(id);

        var response = mockMvc
                .perform(get(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidImage().build())))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Image with id \"" + id + "\" not found");

    }

    @Test
    void shouldReturn404NotRoundWhenImageDoesNotExistOnGet() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.empty()).given(imageRepository).findById(id);

        var response = mockMvc
                .perform(get(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidImage().build())))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Image with id \"" + id + "\" not found");

    }

    @Test
    void shouldReturn200OKWhenGettingImageEntity() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.of(createValidImage().id(id).build())).given(imageRepository).findById(id);

        var response = mockMvc.perform(get(TEST_ENDPOINT + "/" + id)).andExpect(status().isOk()).andReturn();

        var unpackedResponse = response.getResponse().getContentAsString();

        assertThat(unpackedResponse).isNotEmpty();
        assertThat(unpackedResponse).contains(id.toString());
        assertThat(unpackedResponse).contains(TEST_TITLE);
        assertThat(unpackedResponse).contains(TEST_URL);
        assertThat(unpackedResponse).contains(TEST_DESCRIPTION);

    }

    @Test
    void shouldReturn200OKWhenCreatingImage() throws Exception {

        willReturn(createValidImage().build()).given(imageRepository).save(any());

        mockMvc.perform(post(TEST_ENDPOINT + "/").contentType(MediaType.APPLICATION_JSON)
                .content(TestHelper.serializeObject(createValidImage().build()))).andExpect(status().isOk())
                .andReturn();

        var argumentCapture = ArgumentCaptor.forClass(ImageEntity.class);
        verify(imageRepository).save(argumentCapture.capture());

        var imageEntity = argumentCapture.getValue();
        assertThat(imageEntity.getId()).isNotNull();

    }

    @Test
    void shouldReturn400WhenImageIsNotValidUUIDForDelete() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.empty()).given(imageRepository).findById(id);

        var response = mockMvc
                .perform(delete(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidImage().build())))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Image with id \"" + id + "\" not found");

    }

    @Test
    void shouldReturn404WhenImageIsNotFoundForDelete() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.empty()).given(imageRepository).findById(id);

        var response = mockMvc
                .perform(delete(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidImage().build())))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Image with id \"" + id + "\" not found");

    }

    @Test
    void shouldReturn200OKWhenDeletingImage() throws Exception {

        var id = UUID.randomUUID();

        willReturn(Optional.of(createValidImage().id(id).build())).given(imageRepository).findById(id);

        mockMvc.perform(delete(TEST_ENDPOINT + "/" + id)).andExpect(status().isOk());

        verify(imageRepository, times(1)).deleteById(eq(id));

    }

    private static ImageEntityBuilder<?, ?> createValidImage() {
        return ImageEntity.builder().title(TEST_TITLE).url(TEST_URL).index(TEST_INDEX).description(TEST_DESCRIPTION);
    }

    private static ImageUpdate.ImageUpdateBuilder createValidImageBuilder() {
        return ImageUpdate.builder().title(TEST_TITLE).url(TEST_URL).index(TEST_INDEX).description(TEST_DESCRIPTION);
    }

}
