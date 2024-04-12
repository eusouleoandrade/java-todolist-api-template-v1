package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UpdateTodoUseCaseRequestTest {

    @DisplayName("Should execute successfully")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false"
    })
    public void shouldExecuteSuccessfully(long id, String title, Boolean done) {

        // Arranje
        UpdateTodoUseCaseRequest request;

        // Act
        request = new UpdateTodoUseCaseRequest(id, title, done);

        // Assert
        assertThat(request).isNotNull();

        assertEquals(id, request.getId());
        assertEquals(title, request.getTitle());
        assertEquals(done, request.getDone());

        assertThat(request.hasErrorNotification()).isFalse();
    }

    @DisplayName("Should not execute when id is invalid")
    @ParameterizedTest
    @CsvSource({
            "0, Title 1., true",
            "-1, Title 2., false"
    })
    public void shouldNotExecute_WhenIdIsInvalid(long id, String title, Boolean done) {

        // Arranje
        UpdateTodoUseCaseRequest request;

        // Act
        request = new UpdateTodoUseCaseRequest(id, title, done);

        // Assert
        assertThat(request).isNotNull();

        assertEquals(id, request.getId());
        assertEquals(title, request.getTitle());
        assertEquals(done, request.getDone());

        assertThat(request.hasErrorNotification()).isTrue();
        assertThat(request.hasSuccessNotification()).isFalse();

        var errorNotifications = request.getErrorNotifications();
        assertThat(errorNotifications).hasSize(1);
        assertThat(errorNotifications.get(0).getKey()).isEqualTo("COD0005");
        assertThat(errorNotifications.get(0).getMessage()).isEqualTo(String.format("Identifier %s is invalid.", id));
    }

    @DisplayName("Should not execute when id is invalid")
    @Test
    public void shouldNotExecute_WhenTitleIsInvalid() {

        // Arranje
        UpdateTodoUseCaseRequest request;
        long id = 1;
        Boolean done = false;
        String[] titles = { null, "", " " };

        for (String title : titles) {

            // Act
            request = new UpdateTodoUseCaseRequest(id, title, done);

            // Assert
            assertThat(request).isNotNull();

            assertEquals(id, request.getId());
            assertEquals(title, request.getTitle());
            assertEquals(done, request.getDone());

            assertThat(request.hasErrorNotification()).isTrue();
            assertThat(request.hasSuccessNotification()).isFalse();

            var errorNotifications = request.getErrorNotifications();
            assertThat(errorNotifications).hasSize(1);
            assertThat(errorNotifications.get(0).getKey()).isEqualTo("COD0002");
            assertThat(errorNotifications.get(0).getMessage()).isEqualTo("Title is required.");
        }
    }
}