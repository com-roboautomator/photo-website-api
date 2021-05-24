package com.roboautomator.app.component.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;

import java.util.Optional;
import java.util.UUID;

import com.roboautomator.app.component.collection.CollectionEntity.CollectionEntityBuilder;
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
@WebMvcTest(CollectionRepository.class)
public class CollectionControllerTest extends AbstractMockMvcTest {

        private static final String TEST_ENDPOINT = "/collection";
        private static final String TEST_TITLE = "test-title";
        private static final Integer TEST_INDEX = 0;
        private static final String TEST_TAG_COLOUR = "test-tag-colour";
        private static final String TEST_TAG_TITLE = "test-tag-title";
        private static final Integer TEST_TITLE_IMAGE = 1;

        private MockMvc mockMvc;

        @BeforeEach
        void setupMockMvc() throws JsonProcessingException {
                var collectionController = new CollectionController(collectionRepository);
                mockMvc = MockMvcBuilders.standaloneSetup(collectionController)
                                .setControllerAdvice(CollectionControllerAdvice.class).build();
        }

        @Test
        void shouldReturn400WhenCollectionIdIsNotValidUUIDForUpdates() throws Exception {
                var id = "invalid-UUID";

                var response = mockMvc
                                .perform(put(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON).content(
                                                TestHelper.serializeObject(createValidCollectionBuilder().build())))
                                .andExpect(status().isBadRequest()).andReturn();

                verifyNoInteractions(collectionRepository);

                var responseAsString = response.getResponse().getContentAsString();

                assertThat(responseAsString).isNotNull();
                assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
                assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("collectionId");
                assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                                .contains("invalid-UUID is not a valid UUID");
        }

        @Test
        void shouldReturn404NotFoundWhenEntryDoesNotExistOnUpdate() throws Exception {

                var id = UUID.randomUUID();
                willReturn(Optional.empty()).given(collectionRepository).findById(id);

                var response = mockMvc
                                .perform(put(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON).content(
                                                TestHelper.serializeObject(createValidCollectionBuilder().build())))
                                .andExpect(status().isNotFound()).andReturn();

                var responseAsString = response.getResponse().getContentAsString();

                assertThat(responseAsString).isNotNull();
                assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                                .isEqualTo("Collection with id \"" + id + "\" not found");
        }

        @Test
        public void shouldreturn200OKWhenUpdatingExistingCollectionEntity() throws Exception {

                var id = UUID.randomUUID();
                willReturn(Optional.of(createValidEntity().title("previous-title").tagTitle("previous-tag-title")
                                .tagColour("previous-tag-colour").id(id).build())).given(collectionRepository)
                                                .findById(id);

                var response = mockMvc
                                .perform(put(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON).content(
                                                TestHelper.serializeObject(createValidCollectionBuilder().build())))
                                .andExpect(status().isOk()).andReturn();

                var unpackedResponse = response.getResponse().getContentAsString();

                assertThat(unpackedResponse).isNotEmpty();
                assertThat(unpackedResponse).doesNotContain("previous-title");
                assertThat(unpackedResponse).doesNotContain("previous-tag-title");
                assertThat(unpackedResponse).doesNotContain("previous-tag-colour");

                assertThat(unpackedResponse).contains(id.toString());
                assertThat(unpackedResponse).contains("test-title");
                assertThat(unpackedResponse).contains("test-tag-colour");
                assertThat(unpackedResponse).contains("test-tag-title");

        }

        @Test
        void shouldReturn400WhenCollectionIdIsNotValidUUIDForGet() throws Exception {
                var id = "invalid-UUID";

                var response = mockMvc.perform(get(TEST_ENDPOINT + "/" + id).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isBadRequest()).andReturn();

                verifyNoInteractions(collectionRepository);

                var responseAsString = response.getResponse().getContentAsString();

                assertThat(responseAsString).isNotNull();
                assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
                assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("collectionId");
                assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                                .contains("invalid-UUID is not a valid UUID");
        }

        @Test
        void shouldReturn404NotFoundWhenEntryDoesNotExistOnGet() throws Exception {

                var id = UUID.randomUUID();
                willReturn(Optional.empty()).given(collectionRepository).findById(id);

                var response = mockMvc.perform(get(TEST_ENDPOINT + "/" + id)).andExpect(status().isNotFound())
                                .andReturn();

                var responseAsString = response.getResponse().getContentAsString();

                assertThat(responseAsString).isNotNull();
                assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                                .isEqualTo("Collection with id \"" + id + "\" not found");
        }

        @Test
        void shouldReturn200OKWhenGettingCollectionEntity() throws Exception {

                var id = UUID.randomUUID();

                willReturn(Optional.of(createValidEntity().id(id).build())).given(collectionRepository).findById(id);

                var response = mockMvc.perform(get(TEST_ENDPOINT + "/" + id)).andExpect(status().isOk()).andReturn();

                var unpackedResponse = response.getResponse().getContentAsString();

                assertThat(unpackedResponse).isNotEmpty();
                assertThat(unpackedResponse).contains(id.toString());
                assertThat(unpackedResponse).contains("test-title");
                assertThat(unpackedResponse).contains("test-tag-colour");
                assertThat(unpackedResponse).contains("test-tag-title");
        }

        @Test
        void shouldReturn200OKWhenCreatingCollection() throws Exception {

                willReturn(createValidEntity().build()).given(collectionRepository).save(any());

                // When - POST something.com/collection/
                mockMvc.perform(post(TEST_ENDPOINT + "/").contentType(MediaType.APPLICATION_JSON)
                                .content(TestHelper.serializeObject(createValidCollectionBuilder().build())))
                                .andExpect(status().isOk()).andReturn();

                // Then
                var argumentCapcture = ArgumentCaptor.forClass(CollectionEntity.class);
                verify(collectionRepository).save(argumentCapcture.capture());

                var collectionEntity = argumentCapcture.getValue();
                assertThat(collectionEntity.getId()).isNotNull();

        }

        @Test
        void shouldReturn200OKWhenDeletingEntity() throws JsonProcessingException, Exception {

                var id = UUID.randomUUID();

                willReturn(Optional.of(createValidEntity().id(id).build())).given(collectionRepository).findById(id);

                // When - DELETE something.com/collection/
                mockMvc.perform(delete(TEST_ENDPOINT + "/" + id)).andExpect(status().isOk());

                // Then
                verify(collectionRepository, times(1)).deleteById(eq(id));

        }

        @Test
        void shouldReturn400WhenCollectionIsNotValideUUIDForDelete() throws Exception {
                var id = "invalid-UUID";

                var response = mockMvc.perform(delete(TEST_ENDPOINT + "/" + id)).andExpect(status().isBadRequest())
                                .andReturn();

                verifyNoInteractions(collectionRepository);

                var responseAsString = response.getResponse().getContentAsString();

                assertThat(responseAsString).isNotNull();
                assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
                assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("collectionId");
                assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error"))
                                .contains("invalid-UUID is not a valid UUID");

        }

        @Test
        void shouldReturn404NotFoundWhenEntryDoesNotExistOnDelete() throws Exception {

                var id = UUID.randomUUID();
                willReturn(Optional.empty()).given(collectionRepository).findById(id);

                var response = mockMvc.perform(delete(TEST_ENDPOINT + "/" + id)).andExpect(status().isNotFound())
                                .andReturn();

                var responseAsString = response.getResponse().getContentAsString();

                assertThat(responseAsString).isNotNull();
                assertThat(JsonPath.<String>read(responseAsString, "$.message"))
                                .isEqualTo("Collection with id \"" + id + "\" not found");

        }

        private static CollectionEntityBuilder<?, ?> createValidEntity() {
                return CollectionEntity.builder().id(UUID.randomUUID()).title("test-title").index(0)
                                .tagColour("test-tag-colour").tagTitle("test-tag-title").titleImage(1);
        }

        private static CollectionUpdate.CollectionUpdateBuilder createValidCollectionBuilder() {
                return CollectionUpdate.builder().title(TEST_TITLE).index(TEST_INDEX).tagColour(TEST_TAG_COLOUR)
                                .tagTitle(TEST_TAG_TITLE).titleImage(TEST_TITLE_IMAGE);
        }
}
