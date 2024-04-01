package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UpdateTodoRequestTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "Fazer compras., true",
            "Efetuar investimentos., false"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(String title, Boolean done) {

        // Arranje
        UpdateTodoRequest request;

        // Act
        request = new UpdateTodoRequest(title, done);

        // Assert
        assertThat(request).isNotNull();

        assertEquals(title, request.title);
        assertEquals(done, request.done);
    }

    @DisplayName("Should execute successfully when to use the constructor")
    @ParameterizedTest
    @CsvSource({
            "Fazer compras., true",
            "Efetuar investimentos., false"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheCtor(String title, Boolean done) {

        // Arranje
        UpdateTodoRequest request;

        // Act
        request = new UpdateTodoRequest();
        request.title = title;
        request.done = done;

        // Assert
        assertThat(request).isNotNull();

        assertEquals(title, request.title);
        assertEquals(done, request.done);
    }
}