package com.mycompany.javatodolistapitemplatev1.application.mappers;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CreateTodoRequestMapperTest {

    @ParameterizedTest
    @CsvSource({
            "Title 1.",
            "Title 2.",
            "Title 3."
    })
    @DisplayName("Should execute successfully the conversion to CreateTodoUseCaseRequest")
    public void shouldExecuteSuccessfullyTheConversionTo_CreateTodoUseCaseRequest(String title) {

        // Arrange
        CreateTodoRequestMapper mapper = new CreateTodoRequestMapper();
        CreateTodoRequest createTodoRequest = new CreateTodoRequest(title);

        // Act
        var createTodoUseCaseRequest = mapper.convertCreateTodoUseCaseRequest(createTodoRequest);

        // Assert
        assertNotNull(createTodoUseCaseRequest);

        assertEquals(createTodoRequest.getTitle(), createTodoUseCaseRequest.getTitle());
    }
}