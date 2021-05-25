package com.roboautomator.app.component.collection;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roboautomator.app.component.image.ImageEntity;
import com.roboautomator.app.component.util.TestHelper;

import org.assertj.core.groups.Tuple;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CollectionControllerTestIT {

    private static final String TEST_ENDPOINT = "/collection";
    private static final String TEST_TITLE = "test-title";
    private static final Integer TEST_INDEX = 0;
    private static final String TEST_TAG_COLOUR = "test-tag-colour";
    private static final String TEST_TAG_TITLE = "test-tag-title";
    private static final Integer TEST_TITLE_IMAGE = 1;

    private static final CollectionUpdate.CollectionUpdateBuilder VALID_COLLECTION_UPDATE_BUILDER = CollectionUpdate
            .builder().title(TEST_TITLE + "-update").index(TEST_INDEX + 1).tagColour(TEST_TAG_COLOUR + "-update")
            .tagTitle(TEST_TAG_TITLE + "-update").titleImage(TEST_TITLE_IMAGE + 1);

    private static final CollectionEntity VALID_COLLECTION = CollectionEntity.builder().title(TEST_TITLE)
            .index(TEST_INDEX).tagColour(TEST_TAG_COLOUR).tagTitle(TEST_TAG_TITLE).titleImage(TEST_TITLE_IMAGE).build();

    @LocalServerPort
    private int port;

    private URL baseUrl;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private CollectionRepository collectionRepository;

    @BeforeEach
    void setBaseUrl() throws MalformedURLException {
        baseUrl = URI.create("http://localhost:" + port + TEST_ENDPOINT).toURL();
    }

    @Test
    void shouldSaveNewCollectionToDatabase() throws JsonProcessingException, JSONException {

        var update = TestHelper.serializeObject(VALID_COLLECTION);
        var response = template.postForEntity(baseUrl.toString(), TestHelper.getHttpEntity(update), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // get the id from the new collection
        UUID id = UUID.fromString(response.getBody().replaceAll("\"", ""));

        var collection = collectionRepository.findById(id);

        assertThat(collection).isPresent();
        assertThat(collection.get().getTitle()).isEqualTo(TEST_TITLE);
        assertThat(collection.get().getIndex()).isEqualTo(TEST_INDEX);
        assertThat(collection.get().getTagTitle()).isEqualTo(TEST_TAG_TITLE);
        assertThat(collection.get().getTagColour()).isEqualTo(TEST_TAG_COLOUR);
        assertThat(collection.get().getTitleImage()).isEqualTo(TEST_TITLE_IMAGE);

        // reset the database
        collectionRepository.deleteById(id);

    }

    @Test
    void shouldGetCollectionFromDatabaseUsingId() throws JsonProcessingException, JSONException {

        var id = UUID.randomUUID();

        collectionRepository
                .save(CollectionEntity.builder().id(id).build().update(VALID_COLLECTION_UPDATE_BUILDER.build()));

        var response = template.getForEntity(baseUrl.toString() + "/" + id, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject body = TestHelper.parseJson(response.getBody());

        assertThat(body.get("title")).isEqualTo(TEST_TITLE + "-update");
        assertThat(body.get("index")).isEqualTo(TEST_INDEX + 1);
        assertThat(body.get("tagTitle")).isEqualTo(TEST_TAG_TITLE + "-update");
        assertThat(body.get("tagColour")).isEqualTo(TEST_TAG_COLOUR + "-update");
        assertThat(body.get("titleImage")).isEqualTo(TEST_TITLE_IMAGE + 1);

        // clean up
        collectionRepository.deleteById(id);

    }

    @Test
    void shouldGetAllCollectionsFromDatabase() throws JSONException {

        var id1 = UUID.randomUUID();
        var id2 = UUID.randomUUID();

        collectionRepository
                .save(CollectionEntity.builder().title("test-title").titleImage(0).index(0).id(id1).build());
        collectionRepository
                .save(CollectionEntity.builder().title("test-title").titleImage(0).index(0).id(id2).build());

        var response = template.getForEntity(baseUrl.toString(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var collections = TestHelper.parseJsonArray(response.getBody());

        assertThat(collections.length()).isEqualTo(3); // allow for the initial test entry
        assertThat(collections.getJSONObject(1).get("id").toString()).contains(id1.toString());
        assertThat(collections.getJSONObject(2).get("id").toString()).contains(id2.toString());

        // clean up
        collectionRepository.deleteById(id1);
        collectionRepository.deleteById(id2);

    }

    @Test
    void shouldUpdateCollectionFromDatabase() throws RestClientException, JsonProcessingException, JSONException {

        var id = UUID.randomUUID();

        collectionRepository
                .save(CollectionEntity.builder().id(id).build().update(VALID_COLLECTION_UPDATE_BUILDER.build()));

        var savedCollection = collectionRepository.findById(id);

        // Confirm that the entity has saved with update values
        assertThat(savedCollection).isPresent();
        assertThat(savedCollection.get().getId()).isEqualTo(id);
        assertThat(savedCollection.get().getTitle()).isEqualTo(TEST_TITLE + "-update");
        assertThat(savedCollection.get().getIndex()).isEqualTo(TEST_INDEX + 1);
        assertThat(savedCollection.get().getTagTitle()).isEqualTo(TEST_TAG_TITLE + "-update");
        assertThat(savedCollection.get().getTagColour()).isEqualTo(TEST_TAG_COLOUR + "-update");
        assertThat(savedCollection.get().getTitleImage()).isEqualTo(TEST_TITLE_IMAGE + 1);

        var response = template.exchange(baseUrl.toString() + "/" + id, HttpMethod.PUT,
                TestHelper.getHttpEntity(TestHelper.serializeObject(VALID_COLLECTION)), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject body = TestHelper.parseJson(response.getBody());

        // confirm that the object has been updated with valid collection values
        assertThat(body.get("title")).isEqualTo(TEST_TITLE);
        assertThat(body.get("index")).isEqualTo(TEST_INDEX);
        assertThat(body.get("tagTitle")).isEqualTo(TEST_TAG_TITLE);
        assertThat(body.get("tagColour")).isEqualTo(TEST_TAG_COLOUR);
        assertThat(body.get("titleImage")).isEqualTo(TEST_TITLE_IMAGE);

        // clean up
        collectionRepository.deleteById(id);

    }

    @Test
    void shouldDeleteCollectionFromDatabase() {

        var id = UUID.randomUUID();

        collectionRepository
                .save(CollectionEntity.builder().id(id).build().update(VALID_COLLECTION_UPDATE_BUILDER.build()));

        var savedCollection = collectionRepository.findById(id);

        // Confirm that the entity has saved with update values
        assertThat(savedCollection).isPresent();

        template.delete(baseUrl.toString() + "/" + id, HttpMethod.DELETE);

        // Confirm that the entity is not longer present
        assertThat(collectionRepository.findById(id)).isNotPresent();

    }

    @Test
    void shouldGetImagesFromCollection() {

        var imageId1 = UUID.randomUUID();
        var imageId2 = UUID.randomUUID();

        var collectionEntity = CollectionEntity.builder().id(UUID.randomUUID()).title(TEST_TITLE).index(TEST_INDEX)
                .titleImage(TEST_TITLE_IMAGE).tagTitle("TEST").tagColour("#5d5d5d")
                .images(Set.of(createImage(imageId1), createImage(imageId2))).build();

        var savedEntity = collectionRepository.save(collectionEntity);

        assertThat(savedEntity.getImages())
            .extracting(entity -> entity.getId())
            .contains(imageId1, imageId2);

    }

    private ImageEntity createImage(UUID id) {
        return ImageEntity.builder().id(id).title("test-title").url("https://picsum.photos/id/1000/500").index(0).build();
    }

}
