package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SetDoneTodoRequestTest {

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(Boolean done) {

        // Arranje
        SetDoneTodoRequest request;

        // Act
        request = new SetDoneTodoRequest(done);

        // Assert
        assertThat(request).isNotNull();

        assertEquals(done, request.getDone());
    }

    @DisplayName("Should execute successfully when to use the constructor")
    @ParameterizedTest
    @CsvSource({
            "true",
            "false"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheCtor(Boolean done) {

        // Arranje
        SetDoneTodoRequest request;

        // Act
        request = new SetDoneTodoRequest();
        request.setDone(done);

        // Assert
        assertThat(request).isNotNull();

        assertEquals(done, request.getDone());
    }
}
