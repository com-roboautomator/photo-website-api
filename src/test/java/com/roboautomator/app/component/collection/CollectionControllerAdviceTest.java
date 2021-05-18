package com.roboautomator.app.component.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

import com.jayway.jsonpath.JsonPath;
import com.roboautomator.app.component.util.AbstractMockMvcTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CollectionController.class)
@AutoConfigureMockMvc
public class CollectionControllerAdviceTest extends AbstractMockMvcTest {

    private static final String TEST_ENDPOINT = "/collection";

    private static final String TITLE = "test-title";
    private static final Integer INDEX = 0;
    private static final String TAG_TITLE = "test-tag-title";
    private static final String TAG_COLOUR = "test-tag-colour";
    private static final Integer TITLE_IMAGE = 1;

    private static final String VALID_COLLECTION_UPDATE = "{" + " \"title\": \"" + TITLE + "\"," + " \"index\": "
            + INDEX + "," + " \"tagTitle\": \"" + TAG_TITLE + "\"," + " \"tagColour\": \"" + TAG_COLOUR + "\","
            + " \"images\": []," + " \"titleImage\": " + TITLE_IMAGE + "}";

    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        var collectionController = new CollectionController(collectionRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(collectionController)
                .setControllerAdvice(CollectionControllerAdvice.class).build();
    }

    @Test
    void shouldReturn400WhenCollectionIdIsNotValidUUID() throws Exception {
        var collectionId = "invalid-UUID";

        var response = mockMvc.perform(get(TEST_ENDPOINT + "/" + collectionId).contentType(MediaType.APPLICATION_JSON)
                .content(VALID_COLLECTION_UPDATE)).andExpect(status().isBadRequest()).andReturn();

        verifyNoInteractions(collectionRepository);

        var responseAsString = response.getResponse().getContentAsString();
        System.out.println(responseAsString);
        assertThat(responseAsString).isNotNull();
        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("collectionId");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                .contains("invalid-UUID is not a valid UUID");
    }

    @Test
    void shouldReturn404WhenCollectionNotFound() throws Exception {
        var uuid = UUID.randomUUID();

        willReturn(Optional.empty()).given(collectionRepository).findById(uuid);

        var response = mockMvc.perform(
                get(TEST_ENDPOINT + "/" + uuid.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();


        var responseAsString = response.getResponse().getContentAsString();
        assertThat(responseAsString).isNotNull();

        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Collection with id \"" + uuid + "\" not found");
    }

    @Test
    void shouldReturn404WhenCollectionTitleIsBlank() throws Exception {
        String BLANK_TITLE_COLLECTION_UPDATE = "{" + " \"title\": \" \"," + " \"index\": " + INDEX + ","
                + " \"tagTitle\": \"" + TAG_TITLE + "\"," + " \"tagColour\": \"" + TAG_COLOUR + "\","
                + " \"images\": []," + " \"titleImage\": " + TITLE_IMAGE + "}";

        var response = mockMvc.perform(post(TEST_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(BLANK_TITLE_COLLECTION_UPDATE)).andExpect(status().isBadRequest()).andReturn();

        verifyNoInteractions(collectionRepository);

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotNull();
        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("title");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error")).contains("must not be blank");
    }

    @Test
    void shouldReturn404WhenCollectionIndexIsNull() throws Exception {
        String BLANK_TITLE_COLLECTION_UPDATE = "{" + " \"title\": \"" + TITLE + "\","
                + " \"tagTitle\": \"" + TAG_TITLE + "\"," + " \"tagColour\": \"" + TAG_COLOUR + "\","
                + " \"images\": []," + " \"titleImage\": " + TITLE_IMAGE + "}";

        var response = mockMvc.perform(post(TEST_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(BLANK_TITLE_COLLECTION_UPDATE)).andExpect(status().isBadRequest()).andReturn();

        verifyNoInteractions(collectionRepository);

        var responseAsString = response.getResponse().getContentAsString();

        assertThat(responseAsString).isNotNull();
        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("index");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error")).contains("must not be null");  
    }

}
