package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateTodoRequestTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(String title) {

        // Arranje
        CreateTodoRequest request;

        // Act
        request = new CreateTodoRequest(title);

        // Assert
        assertNotNull(request);

        assertEquals(title, request.getTitle());
    }

    @DisplayName("Should execute successfully when to use the constructor")
    @ParameterizedTest
    @CsvSource({
            "Title 1.",
            "Title 2.",
            "Title 3."
    })
    public void shouldExecuteSuccessfully_WhenToUseTheCtor(String title) {

        // Arranje
        CreateTodoRequest request;

        // Act
        request = new CreateTodoRequest();
        request.setTitle(title);

        // Assert
        assertNotNull(request);

        assertEquals(title, request.getTitle());
    }
}