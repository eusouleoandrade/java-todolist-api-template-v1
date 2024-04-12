package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetTodoListUseCaseResponseTest {

    @DisplayName("Should execute successfully when to use the constructor")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheCtor(long id, String title, boolean done) {

        // Arranje
        GetTodoListUseCaseResponse response;

        // Act
        response = new GetTodoListUseCaseResponse();
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