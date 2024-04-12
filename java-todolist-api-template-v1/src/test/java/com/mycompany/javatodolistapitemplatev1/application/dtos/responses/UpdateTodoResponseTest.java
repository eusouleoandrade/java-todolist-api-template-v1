package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UpdateTodoResponseTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @Test
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor() {

        // Arranje
        UpdateTodoResponse response;

        // Act
        response = new UpdateTodoResponse(true);

        // Assert
        assertNotNull(response);

        assertThat(response.isSucceeded()).isTrue();
        assertEquals("Request processed.", response.getMessage());
    }
}