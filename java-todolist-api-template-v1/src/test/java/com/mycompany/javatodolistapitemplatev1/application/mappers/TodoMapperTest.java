package com.mycompany.javatodolistapitemplatev1.application.mappers;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

@SpringBootTest
public class TodoMapperTest {

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private TodoMapper mapper;

    @DisplayName("Should execute successfully the conversion to GetTodoListUseCaseResponse")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfullyTheConversionTo_GetTodoListUseCaseResponse(long id, String title,
            boolean done) {

        // Arrange
        var todo = new Todo(id, title, done);

        var expectedGetTodoListUseCaseResponse = new GetTodoListUseCaseResponse();
        expectedGetTodoListUseCaseResponse.setId(id);
        expectedGetTodoListUseCaseResponse.setTitle(title);
        expectedGetTodoListUseCaseResponse.setDone(done);

        when(modelMapperMock.map(todo, GetTodoListUseCaseResponse.class))
                .thenReturn(expectedGetTodoListUseCaseResponse);

        // Act
        var convertedGetTodoListUseCaseResponse = mapper.convertGetTodoListUseCaseResponse(todo);

        // Assert
        assertNotNull(convertedGetTodoListUseCaseResponse);

        assertEquals(expectedGetTodoListUseCaseResponse, convertedGetTodoListUseCaseResponse);
    }

    @DisplayName("Should execute successfully the conversion to TodoUseCaseResponse")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfullyTheConversionTo_TodoUseCaseResponse(long id, String title, boolean done) {

        // Arrange
        var todo = new Todo(id, title, done);

        var expectedTodoUseCaseResponse = new TodoUseCaseResponse();
        expectedTodoUseCaseResponse.setId(id);
        expectedTodoUseCaseResponse.setTitle(title);
        expectedTodoUseCaseResponse.setDone(done);

        when(modelMapperMock.map(todo, TodoUseCaseResponse.class)).thenReturn(expectedTodoUseCaseResponse);

        // Act
        var convertTodoUseCaseResponse = mapper.convertTodoUseCaseResponse(todo);

        // Assert
        assertNotNull(convertTodoUseCaseResponse);

        assertEquals(expectedTodoUseCaseResponse, convertTodoUseCaseResponse);
    }
}