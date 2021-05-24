package com.roboautomator.app.component.image;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roboautomator.app.component.image.ImageEntity.ImageEntityBuilder;
import com.roboautomator.app.component.util.TestHelper;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageControllerTestIT {

    private static final String TEST_ENDPOINT = "/image";

    private static final String TEST_TITLE = "test-title";
    private static final String TEST_URL = "test-url";
    private static final Integer TEST_INDEX = 0;
    private static final String TEST_DESCRIPTION = "test-description";

    @LocalServerPort
    private int port;

    private URL baseUrl;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    void setBaseUrl() throws MalformedURLException {
        baseUrl = URI.create("http://localhost:" + port + TEST_ENDPOINT).toURL();
    }

    @Test
    void shouldSaveNewImageToDatabase() throws JsonProcessingException {

        var update = TestHelper.serializeObject(createValidImage().build());
        var response = template.postForEntity(baseUrl.toString(), TestHelper.getHttpEntity(update), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // get the id from the new collection
        UUID id = UUID.fromString(response.getBody().replaceAll("\"", ""));

        var image = imageRepository.findById(id);

        assertThat(image).isPresent();
        assertThat(image.get().getTitle()).isEqualTo(TEST_TITLE);
        assertThat(image.get().getUrl()).isEqualTo(TEST_URL);
        assertThat(image.get().getIndex()).isEqualTo(TEST_INDEX);
        assertThat(image.get().getDescription()).isEqualTo(TEST_DESCRIPTION);

        // reset the database
        imageRepository.deleteById(id);
    }

    @Test
    void shouldGetImageFromDatabaseUsingId() throws JSONException {

        var id = UUID.randomUUID();

        imageRepository.save(((ImageEntityBuilder<?, ?>) createValidImage().id(id)).build());

        var response = template.getForEntity(baseUrl.toString() + "/" + id, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject body = TestHelper.parseJson(response.getBody());

        assertThat(body.get("title")).isEqualTo(TEST_TITLE);
        assertThat(body.get("url")).isEqualTo(TEST_URL);
        assertThat(body.get("index")).isEqualTo(TEST_INDEX);
        assertThat(body.get("description")).isEqualTo(TEST_DESCRIPTION);

    }

    @Test
    void shouldUpdateImageFromDatabase() throws RestClientException, JsonProcessingException, JSONException {

        var id = UUID.randomUUID();

        imageRepository.save((ImageEntity) createValidImage().id(id).build());

        var savedImage = imageRepository.findById(id);

        // Confirm the image has saved with origional values
        assertThat(savedImage.isPresent());
        assertThat(savedImage.get().getId()).isEqualTo(id);
        assertThat(savedImage.get().getTitle()).isEqualTo(TEST_TITLE);
        assertThat(savedImage.get().getUrl()).isEqualTo(TEST_URL);
        assertThat(savedImage.get().getIndex()).isEqualTo(TEST_INDEX);
        assertThat(savedImage.get().getDescription()).isEqualTo(TEST_DESCRIPTION);

        var response = template.exchange(baseUrl.toString() + "/" + id, HttpMethod.PUT,
                TestHelper.getHttpEntity(TestHelper.serializeObject(createValidImageUpdate().build())), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject body = TestHelper.parseJson(response.getBody());

        // confirm that the object has been updated with valid image update values
        assertThat(body.get("title")).isEqualTo(TEST_TITLE + "-update");
        assertThat(body.get("url")).isEqualTo(TEST_URL + "-update");
        assertThat(body.get("index")).isEqualTo(TEST_INDEX + 1);
        assertThat(body.get("description")).isEqualTo(TEST_DESCRIPTION + "-update");

        // clean up
        imageRepository.deleteById(id);
    }

    @Test
    void shouldDeleteImageFromDatabase() {

        var id = UUID.randomUUID();

        imageRepository.save((ImageEntity) createValidImage().id(id).build());

        var savedImage = imageRepository.findById(id);

        // Confirm that the entity has saved with values
        assertThat(savedImage).isPresent();

        template.delete(baseUrl.toString() + "/" + id, HttpMethod.DELETE);

        // Confirm that the images is no longer part of the database
        assertThat(imageRepository.findById(id)).isNotPresent();
    }

    private ImageEntityBuilder<?, ?> createValidImage() {
        return ImageEntity.builder().title(TEST_TITLE).url(TEST_URL).index(TEST_INDEX).description(TEST_DESCRIPTION);
    }

    private ImageUpdate.ImageUpdateBuilder createValidImageUpdate() {
        return ImageUpdate.builder().title(TEST_TITLE + "-update").url(TEST_URL + "-update").index(TEST_INDEX + 1)
                .description(TEST_DESCRIPTION + "-update");
    }

}
