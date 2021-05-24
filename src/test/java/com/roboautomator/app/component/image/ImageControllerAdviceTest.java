package com.roboautomator.app.component.image;

import com.jayway.jsonpath.JsonPath;
import com.roboautomator.app.component.image.ImageEntity.ImageEntityBuilder;
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
@SpringBootTest(classes = ImageController.class)
@AutoConfigureMockMvc
public class ImageControllerAdviceTest extends AbstractMockMvcTest {

    private static final String TEST_ENDPOINT = "/image";

    private static final String TITLE = "test-title";
    private static final String URL = "test-url";
    private static final Integer INDEX = 0;
    private static final String DESCRIPTION = "test-description";

    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        var imageController = new ImageController(imageRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).setControllerAdvice(ImageControllerAdvice.class)
                .build();
    }

    @Test
    void shouldReturn400WhenImageIdIsNotValidUUID() throws Exception {

        var imageId = "invalid-UUID";

        var response = mockMvc
                .perform(get(TEST_ENDPOINT + "/" + imageId).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidImage().build())))
                .andExpect(status().isBadRequest()).andReturn();

        verifyNoInteractions(imageRepository);

        var responseAsString = response.getResponse().getContentAsString();
        System.out.println(responseAsString);
        assertThat(responseAsString).isNotNull();
        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("imageId");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                .contains("invalid-UUID is not a valid UUID");

    }

    @Test
    void shouldReturn404WhenImageNotFound() throws Exception {

        var uuid = UUID.randomUUID();

        willReturn(Optional.empty()).given(imageRepository).findById(uuid);

        var response = mockMvc
                .perform(get(TEST_ENDPOINT + "/" + uuid.toString()).contentType(MediaType.APPLICATION_JSON)
                        .content(TestHelper.serializeObject(createValidImage().build())))
                .andExpect(status().isNotFound()).andReturn();

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotEmpty();
        assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                .isEqualTo("Image with id \"" + uuid + "\" not found");

    }

    private ImageEntityBuilder<?, ?> createValidImage() {
        return ImageEntity.builder().title(TITLE).url(URL).index(INDEX).description(DESCRIPTION);
    }

}
