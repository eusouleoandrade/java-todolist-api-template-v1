package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;

@SpringBootTest
public class CreateTodoResponseTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "1, Fazer compras., true",
            "2, Efetuar investimentos., false",
            "3, Analisar planejamento financeiro., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(long id, String title, Boolean done) {

        // Arranje
        TodoQuery todoQuery = new TodoQuery(id, title, done);

        // Act
        CreateTodoResponse response = new CreateTodoResponse(todoQuery);

        // Asser
        assertNotNull(response);

        assertEquals(todoQuery, response.getTodo());

        assertThat(response.isSucceeded()).isTrue();
        assertEquals(response.getMessage(), "Request processed.");
    }

    @DisplayName("Should execute successfully when to use the set todo")
    @ParameterizedTest
    @CsvSource({
            "1, Fazer compras., true",
            "2, Efetuar investimentos., false",
            "3, Analisar planejamento financeiro., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseSetTodo(long id, String title, boolean done) {

        // Arranje
        TodoQuery todoQuery = new TodoQuery(id, title, done);

        // Act
        CreateTodoResponse response = new CreateTodoResponse(null);
        response.setTodo(todoQuery);

        // Asser
        assertNotNull(response);

        assertEquals(todoQuery, response.getTodo());

        assertThat(response.isSucceeded()).isTrue();
        assertEquals("Request processed.", response.getMessage());
    }
}