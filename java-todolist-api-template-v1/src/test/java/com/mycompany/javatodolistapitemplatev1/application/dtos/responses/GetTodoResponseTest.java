package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetTodoResponseTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(long id, String title, boolean done) {

        // Arranje
        GetTodoResponse response;

        TodoQuery todoQuery = new TodoQuery(id, title, done);

        // Act
        response = new GetTodoResponse(todoQuery);

        // Assert
        assertNotNull(response);

        assertEquals(todoQuery, response.getTodo());

        assertThat(response.getTodo()).isNotNull();

        assertThat(response.isSucceeded()).isTrue();
        assertEquals("Request processed.", response.getMessage());
    }

    @DisplayName("Should execute successfully when to use the set todo")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseSetTodo(long id, String title, boolean done) {

        // Arranje
        GetTodoResponse response;

        TodoQuery todoQuery = new TodoQuery(id, title, done);

        // Act
        response = new GetTodoResponse(null);
        response.setTodo(todoQuery);

        // Assert
        assertNotNull(response);

        assertEquals(todoQuery, response.getTodo());

        assertThat(response.getTodo()).isNotNull();

        assertThat(response.isSucceeded()).isTrue();
        assertEquals("Request processed.", response.getMessage());
    }
}
