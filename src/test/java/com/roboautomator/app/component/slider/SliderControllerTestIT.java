package com.roboautomator.app.component.slider;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roboautomator.app.component.slider.SliderEntity.SliderEntityBuilder;
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
public class SliderControllerTestIT {
    
    private static final String TEST_ENDPOINT = "/slider";

    private static final String TITLE = "test-title";
    private static final String COLOUR = "test-colour";

    @LocalServerPort
    private int port;

    private URL baseUrl;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private SliderRepository sliderRepository;

    @BeforeEach
    void setBaseUrl() throws MalformedURLException {
        baseUrl = URI.create("http://localhost:" + port + TEST_ENDPOINT).toURL();
    }

    @Test
    void shouldSaveNewSliderToDatabase() throws JsonProcessingException {

        var update = TestHelper.serializeObject(createValidSlider().build());
        var response = template.postForEntity(baseUrl.toString(), TestHelper.getHttpEntity(update), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // get the id from the new collection
        UUID id = UUID.fromString(response.getBody().replaceAll("\"", ""));

        var slider = sliderRepository.findById(id);

        assertThat(slider).isPresent();
        assertThat(slider.get().getTitle()).isEqualTo(TITLE);
        assertThat(slider.get().getColour()).isEqualTo(COLOUR);

        // reset the database
        sliderRepository.deleteById(id);
    }

    @Test
    void shouldGetSliderFromDatabaseUsingId() throws JSONException {

        var id = UUID.randomUUID();

        sliderRepository.save(((SliderEntityBuilder<?, ?>) createValidSlider().id(id)).build());

        var response = template.getForEntity(baseUrl.toString() + "/" + id, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject body = TestHelper.parseJson(response.getBody());

        assertThat(body.get("title")).isEqualTo(TITLE);
        assertThat(body.get("colour")).isEqualTo(COLOUR);

    }

    @Test
    void shouldReturnGetAllSlidersFromDatabase() throws JSONException{

        var response = template.getForEntity(baseUrl.toString(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var responseAsArray = TestHelper.parseJsonArray(response.getBody());

        assertThat(responseAsArray.getJSONObject(0).get("id").toString()).contains("5913905d-059a-4b07-93e2-c97a1428c78e");
        assertThat(responseAsArray.getJSONObject(1).get("id").toString()).contains("5a75137b-475d-4d98-aaee-40d0e78f5bc1");

    }

    @Test
    void shouldUpdateSliderFromDatabase() throws RestClientException, JsonProcessingException, JSONException {

        var id = UUID.randomUUID();

        sliderRepository.save((SliderEntity) createValidSlider().id(id).build());

        var savedSlider = sliderRepository.findById(id);

        // Confirm the image has saved with origional values
        assertThat(savedSlider.isPresent());
        assertThat(savedSlider.get().getId()).isEqualTo(id);
        assertThat(savedSlider.get().getTitle()).isEqualTo(TITLE);
        assertThat(savedSlider.get().getColour()).isEqualTo(COLOUR);

        var response = template.exchange(baseUrl.toString() + "/" + id, HttpMethod.PUT,
                TestHelper.getHttpEntity(TestHelper.serializeObject(createValidSliderUpdate().build())), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        JSONObject body = TestHelper.parseJson(response.getBody());

        // confirm that the object has been updated with valid image update values
        assertThat(body.get("title")).isEqualTo(TITLE + "-update");
        assertThat(body.get("colour")).isEqualTo(COLOUR + "-update");

        // clean up
        sliderRepository.deleteById(id);
    }

    @Test
    void shouldDeleteSliderFromDatabase() {

        var id = UUID.randomUUID();

        sliderRepository.save((SliderEntity) createValidSlider().id(id).build());

        var savedSlider = sliderRepository.findById(id);

        // Confirm that the entity has saved with values
        assertThat(savedSlider).isPresent();

        template.delete(baseUrl.toString() + "/" + id, HttpMethod.DELETE);

        // Confirm that the images is no longer part of the database
        assertThat(sliderRepository.findById(id)).isNotPresent();
    }


    private static SliderEntityBuilder<?, ?> createValidSlider() {
        return SliderEntity.builder().title(TITLE).colour(COLOUR);
    }

    private static SliderUpdate.SliderUpdateBuilder createValidSliderUpdate() {
        return SliderUpdate.builder().title(TITLE + "-update").colour(COLOUR + "-update");
    }

}
