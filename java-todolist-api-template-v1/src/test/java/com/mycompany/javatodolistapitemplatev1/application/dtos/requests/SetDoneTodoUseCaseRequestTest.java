package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SetDoneTodoUseCaseRequestTest {

    @DisplayName("Should execute successfully")
    @ParameterizedTest
    @CsvSource({
            "1, true",
            "2, false"
    })
    public void shouldExecuteSuccessfully(long id, Boolean done) {

        // Arranje
        SetDoneTodoUseCaseRequest request;

        // Act
        request = new SetDoneTodoUseCaseRequest(id, done);

        // Assert
        assertThat(request).isNotNull();

        assertEquals(id, request.getId());
        assertEquals(done, request.getDone());
    }
}