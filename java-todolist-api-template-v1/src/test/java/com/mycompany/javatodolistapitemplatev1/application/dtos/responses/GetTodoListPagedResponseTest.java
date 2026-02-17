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
public class GetTodoListPagedResponseTest {

    private static final int pageNumber = 1;
    private static final int pageSize = 50;
    private static final int totalPages = 3;
    private static final int totalRecords = 130;

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(long id, String title, boolean done) {

        // Arrange
        var todoQuery = new TodoQuery(id, title, done);

        var todoQueryList = new ArrayList<TodoQuery>();
        todoQueryList.add(todoQuery);

        // Act
        GetTodoListPagedResponse response = new GetTodoListPagedResponse(todoQueryList,
                pageNumber,
                pageSize,
                totalPages,
                totalRecords);

        // Assert
        assertNotNull(response);

        assertEquals(pageNumber, response.getPageNumber());
        assertEquals(pageSize, response.getPageSize());
        assertEquals(totalPages, response.getTotalPages());
        assertEquals(totalRecords, response.getTotalRecords());

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

        // Arrange
        var todoQuery = new TodoQuery(id, title, done);

        var todoQueryList = new ArrayList<TodoQuery>();
        todoQueryList.add(todoQuery);

        // Act
        GetTodoListPagedResponse response = new GetTodoListPagedResponse(null,
                pageNumber,
                pageSize,
                totalPages,
                totalRecords);

        response.setTodos(todoQueryList);

        // Assert
        assertNotNull(response);

        assertEquals(pageNumber, response.getPageNumber());
        assertEquals(pageSize, response.getPageSize());
        assertEquals(totalPages, response.getTotalPages());
        assertEquals(totalRecords, response.getTotalRecords());

        assertThat(response.getTodos()).isNotEmpty();
        assertThat(response.getTodos()).hasSize(1);

        assertEquals(todoQueryList, response.getTodos());

        assertThat(response.isSucceeded()).isTrue();
        assertEquals("Request processed.", response.getMessage());
    }
}