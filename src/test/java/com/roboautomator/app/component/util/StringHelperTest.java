package com.roboautomator.app.component.util;

import org.junit.jupiter.api.Test;

import static com.roboautomator.app.component.util.StringHelper.cleanString;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.roboautomator.app.component.image.ImageEntity;

class StringHelperTest {

    @Test
    void shouldRemoveNewlineCharacters() {
        var testString = "Hello\nWorld";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    void shouldRemoveCarriageReturnCharacters() {
        var testString = "Hello\rWorld";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    void shouldRemoveTabCharacters() {
        var testString = "Hello\tWorld";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    void shouldRemoveMultipleEscapeCharacters() {
        var testString = "He\nll\ro\t\nWor\r\rl\t\td";

        assertThat(cleanString(testString)).isEqualTo("HelloWorld");
    }

    @Test
    void shouldReturnAllClassVariablesInStringFormat() throws IllegalArgumentException, IllegalAccessException {

        var testClass = ImageEntity.builder().title("test-title").url("test-url").index(0)
                .description("test-description").build();

        var stringResponse = StringHelper.classToString(testClass);

        assertThat(stringResponse).contains("title=test-title");
        assertThat(stringResponse).contains("url=test-url");
        assertThat(stringResponse).contains("index=0");
        assertThat(stringResponse).contains("description=test-description");

    }

    @Test
    void shouldReturnAllSuperClassVariablesInStringFormat() throws IllegalArgumentException, IllegalAccessException {

        var id = UUID.randomUUID();
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var testClass = ImageEntity.builder().id(id).createdAt(createdAt).updatedAt(updatedAt).build();

        var stringResponse = StringHelper.classToString(testClass);

        assertThat(stringResponse).contains("id=" + id);
        assertThat(stringResponse).contains("createdAt=" + createdAt);
        assertThat(stringResponse).contains("updatedAt=" + updatedAt);


    }
}
