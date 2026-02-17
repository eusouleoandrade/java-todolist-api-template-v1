package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GetTodoListResponseTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(long id, String title, boolean done) {

        // Arranje
        var todoQuery = new TodoQuery(id, title, done);

        var todoQueryList = new ArrayList<TodoQuery>();
        todoQueryList.add(todoQuery);

        // Act
        GetTodoListResponse response = new GetTodoListResponse(todoQueryList);

        // Assert
        assertNotNull(response);

        assertThat(response.getTodos()).isNotEmpty();
        assertThat(response.getTodos()).hasSize(1);

        assertEquals(todoQueryList, response.getTodos());

        assertThat(response.isSucceeded()).isTrue();
        assertEquals("Request processed.", response.getMessage());
    }

    @DisplayName("Should execute successfully when to use the set todos")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseSetTodos(long id, String title, boolean done) {

        // Arranje
        var todoQuery = new TodoQuery(id, title, done);

        var todoQueryList = new ArrayList<TodoQuery>();
        todoQueryList.add(todoQuery);

        // Act
        GetTodoListResponse response = new GetTodoListResponse(null);
        response.setTodos(todoQueryList);

        // Assert
        assertNotNull(response);

        assertThat(response.getTodos()).isNotEmpty();
        assertThat(response.getTodos()).hasSize(1);

        assertEquals(todoQueryList, response.getTodos());

        assertThat(response.isSucceeded()).isTrue();
        assertEquals("Request processed.", response.getMessage());
    }
}