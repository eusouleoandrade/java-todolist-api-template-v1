package com.mycompany.javatodolistapitemplatev1.application.mappers;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GetTodoListUseCaseResponseMapperTest {

    @Mock
    private ModelMapper modelMapperMock;

    @InjectMocks
    private GetTodoListUseCaseResponseMapper mapper;

    @ParameterizedTest
    @CsvSource({
            "1, Title 1., true",
            "2, Title 2., false",
            "3, Title 3., true"
    })
    @DisplayName("Shoul execute successfully conversion to TodoQuery")
    public void shouldExecuteSuccessfullyTheConversionTo_TodoQuery(long id, String title, boolean done) {

        // Arrange
        var getTodoListUseCaseResponse = new GetTodoListUseCaseResponse();
        getTodoListUseCaseResponse.setId(id);
        getTodoListUseCaseResponse.setTitle(title);
        getTodoListUseCaseResponse.setDone(done);

        var expectedTodoQuery = new TodoQuery(id, title, done);

        when(modelMapperMock.map(getTodoListUseCaseResponse, TodoQuery.class)).thenReturn(expectedTodoQuery);

        // Act
        var convertTodoQuery = mapper.convertTodoQuery(getTodoListUseCaseResponse);

        // Assert
        assertNotNull(convertTodoQuery);

        assertEquals(expectedTodoQuery, convertTodoQuery);
    }
}