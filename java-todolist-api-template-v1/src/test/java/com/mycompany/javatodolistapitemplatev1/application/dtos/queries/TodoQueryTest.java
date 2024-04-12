package com.mycompany.javatodolistapitemplatev1.application.dtos.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TodoQueryTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(long id, String title, Boolean done) {

        // Arranje
        TodoQuery todoQuery;

        // Act
        todoQuery = new TodoQuery(id, title, done);

        // Assert
        assertNotNull(todoQuery);

        assertEquals(id, todoQuery.getId());
        assertEquals(title, todoQuery.getTitle());
        assertEquals(done, todoQuery.getDone());
    }

    @DisplayName("Should execute successfully when to use the constructor")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheCtor(long id, String title, Boolean done) {

        // Arranje
        TodoQuery todoQuery;

        // Act
        todoQuery = new TodoQuery();
        todoQuery.setId(id);
        todoQuery.setTitle(title);
        todoQuery.setDone(done);

        // Assert
        assertNotNull(todoQuery);

        assertEquals(id, todoQuery.getId());
        assertEquals(title, todoQuery.getTitle());
        assertEquals(done, todoQuery.getDone());
    }
}