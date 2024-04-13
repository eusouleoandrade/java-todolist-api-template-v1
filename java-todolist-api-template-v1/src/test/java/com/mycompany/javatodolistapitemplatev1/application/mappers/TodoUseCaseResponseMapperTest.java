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

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;

@SpringBootTest
public class TodoUseCaseResponseMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TodoUseCaseResponseMapper mapper;

    @DisplayName("Should execute successfully the conversion to TodoQuery")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    public void shouldExecuteSuccessfullyTheConversionTo_TodoQuery(long id, String title, boolean done) {

        // Arranje
        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(id);
        todoUseCaseResponse.setTitle(title);
        todoUseCaseResponse.setDone(done);

        var expectedTodoQuery = new TodoQuery(id, title, done);

        when(modelMapper.map(todoUseCaseResponse, TodoQuery.class)).thenReturn(expectedTodoQuery);

        // Act
        var convertTodoQuery = mapper.convertTodoQuery(todoUseCaseResponse);

        // Assert
        assertNotNull(convertTodoQuery);

        assertEquals(expectedTodoQuery, convertTodoQuery);
    }
}