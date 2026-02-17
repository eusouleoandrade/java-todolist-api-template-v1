package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TodoUseCaseResponseTest {

    @DisplayName("Should execute successfully")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully(long id, String title, boolean done) {

        // Arrange
        TodoUseCaseResponse response;

        // Act
        response = new TodoUseCaseResponse();
        response.setId(id);
        response.setTitle(title);
        response.setDone(done);

        // Assert
        assertNotNull(response);

        assertEquals(id, response.getId());
        assertEquals(title, response.getTitle());
        assertEquals(done, response.isDone());
    }
}