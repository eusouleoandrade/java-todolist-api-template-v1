package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.mycompany.javatodolistapitemplatev1.shared.notification.models.NotificationMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NotificationMessagesResponseTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "COD0001, Notification message 1.",
            "COD0002, Notification message 2.",
            "COD0003, Notification message 3."
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(String key, String message) {

        // Arranje
        NotificationMessagesResponse response;

        var notificationMessage = new NotificationMessage(key, message);

        var notificationMesssageList = new ArrayList<NotificationMessage>();
        notificationMesssageList.add(notificationMessage);

        // Act
        response = new NotificationMessagesResponse(notificationMesssageList);

        // Assert
        assertNotNull(response);

        assertThat(response.getErrors()).isNotEmpty();
        assertThat(response.getErrors()).hasSize(1);

        assertEquals(notificationMesssageList, response.getErrors());

        assertThat(response.isSucceeded()).isFalse();
        assertEquals("Failed to process the request.", response.getMessage());
    }

    @DisplayName("Should execute successfully when to use the set errors")
    @ParameterizedTest
    @CsvSource({
            "COD0001, Notification message 1.",
            "COD0002, Notification message 2.",
            "COD0003, Notification message 3."
    })
    public void shouldExecuteSuccessfully_WhenToUseSetErros(String key, String message) {

        // Arranje
        NotificationMessagesResponse response;

        var notificationMessage = new NotificationMessage(key, message);

        var notificationMesssageList = new ArrayList<NotificationMessage>();
        notificationMesssageList.add(notificationMessage);

        // Act
        response = new NotificationMessagesResponse(null);
        response.setErrors(notificationMesssageList);

        // Assert
        assertNotNull(response);

        assertThat(response.getErrors()).isNotEmpty();
        assertThat(response.getErrors()).hasSize(1);

        assertEquals(notificationMesssageList, response.getErrors());

        assertThat(response.isSucceeded()).isFalse();
        assertEquals("Failed to process the request.", response.getMessage());
    }
}