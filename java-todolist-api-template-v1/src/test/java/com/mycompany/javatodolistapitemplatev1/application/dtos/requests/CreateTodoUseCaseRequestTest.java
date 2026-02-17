package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateTodoUseCaseRequestTest {

    @DisplayName("Should execute successfully")
    @ParameterizedTest
    @CsvSource({
            "Title 1.",
            "Title 2.",
            "Title 3."
    })
    public void shouldExecuteSuccessfully(String title) {

        // Arrange
        CreateTodoUseCaseRequest request;

        // Act
        request = new CreateTodoUseCaseRequest(title);

        // Assert
        assertNotNull(request);

        assertFalse(request.isDone());

        assertEquals(title, request.getTitle());

        assertFalse(request.hasErrorNotification());
    }

    @DisplayName("Should not execute successfully when the title has an invalidvalue")
    @Test
    public void shouldNotExecute_WhenTitleHasInvalidValue() {

        // Arrange
        CreateTodoUseCaseRequest request;
        String[] titles = { null, "", " " };

        for (String title : titles) {

            // Act
            request = new CreateTodoUseCaseRequest(title);

            // Assert
            assertThat(request).isNotNull();

            assertEquals(title, request.getTitle());
            assertThat(request.isDone()).isFalse();

            assertThat(request.hasErrorNotification()).isTrue();
            assertThat(request.hasSuccessNotification()).isFalse();

            var errorNotifications = request.getErrorNotifications();
            assertThat(errorNotifications).hasSize(1);
            assertThat(errorNotifications.get(0).getKey()).isEqualTo("COD0002");
            assertThat(errorNotifications.get(0).getMessage()).isEqualTo("Title is required.");
        }
    }
}