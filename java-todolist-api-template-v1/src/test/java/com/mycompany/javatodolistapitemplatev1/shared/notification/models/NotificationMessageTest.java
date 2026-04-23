package com.mycompany.javatodolistapitemplatev1.shared.notification.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NotificationMessageTest {

    @DisplayName("Should create NotificationMessage with key and message")
    @ParameterizedTest
    @CsvSource({
            "ERR001, Error message 1",
            "ERR002, Error message 2",
            "ERR003, Error message 3"
    })
    public void shouldCreateNotificationMessageWithKeyAndMessage(String key, String message) {

        // Arrange
        // Act
        var notification = new NotificationMessage(key, message);

        // Assert
        assertNotNull(notification);
        assertThat(notification.getKey()).isEqualTo(key);
        assertThat(notification.getMessage()).isEqualTo(message);
    }

    @DisplayName("Should get and set key")
    @Test
    public void shouldGetAndSetKey() {

        // Arrange
        var notification = new NotificationMessage("KEY1", "Message 1");
        String newKey = "KEY2";

        // Act
        notification.setKey(newKey);

        // Assert
        assertThat(notification.getKey()).isEqualTo(newKey);
    }

    @DisplayName("Should get and set message")
    @Test
    public void shouldGetAndSetMessage() {

        // Arrange
        var notification = new NotificationMessage("KEY1", "Message 1");
        String newMessage = "Updated message";

        // Act
        notification.setMessage(newMessage);

        // Assert
        assertThat(notification.getMessage()).isEqualTo(newMessage);
    }

    @DisplayName("Should handle null values in constructor")
    @Test
    public void shouldHandleNullValuesInConstructor() {

        // Arrange
        // Act
        var notification = new NotificationMessage(null, null);

        // Assert
        assertNotNull(notification);
        assertThat(notification.getKey()).isNull();
        assertThat(notification.getMessage()).isNull();
    }

    @DisplayName("Should handle empty strings")
    @Test
    public void shouldHandleEmptyStrings() {

        // Arrange
        String emptyKey = "";
        String emptyMessage = "";

        // Act
        var notification = new NotificationMessage(emptyKey, emptyMessage);

        // Assert
        assertThat(notification.getKey()).isEmpty();
        assertThat(notification.getMessage()).isEmpty();
    }
}