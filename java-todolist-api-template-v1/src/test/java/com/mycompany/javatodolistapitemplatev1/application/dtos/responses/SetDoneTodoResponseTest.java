package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SetDoneTodoResponseTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @Test
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor() {

        // Arranje
        SetDoneTodoResponse response;

        // Act
        response = new SetDoneTodoResponse(true);

        // Assert
        assertNotNull(response);

        assertThat(response.isSucceeded()).isTrue();
        assertEquals("Request processed.", response.getMessage());
    }
}