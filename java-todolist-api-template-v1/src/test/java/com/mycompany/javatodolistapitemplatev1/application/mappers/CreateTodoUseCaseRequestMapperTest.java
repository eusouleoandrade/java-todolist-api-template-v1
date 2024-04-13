package com.mycompany.javatodolistapitemplatev1.application.mappers;

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

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

@SpringBootTest
public class CreateTodoUseCaseRequestMapperTest {

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private CreateTodoUseCaseRequestMapper mapper;

    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    @DisplayName("Should execute successfully the conversion to Todo")
    public void shouldExecuteSuccessfullyTheConversionTo_Todo(long id, String title, boolean done) {

        // Arranje
        var createTodoUseCaseRequest = new CreateTodoUseCaseRequest(title);

        Todo expectedTodo = new Todo(id, title, done);

        when(modelMapperMock.map(createTodoUseCaseRequest, Todo.class)).thenReturn(expectedTodo);

        // Act
        var convertedTodo = mapper.convertTodo(createTodoUseCaseRequest);

        // Assert
        assertNotNull(convertedTodo);

        assertEquals(expectedTodo, convertedTodo);
    }
}