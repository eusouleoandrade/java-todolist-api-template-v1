package com.mycompany.javatodolistapitemplatev1.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.GetPaginatedTodoListsUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

import nl.altindag.log.LogCaptor;

@SpringBootTest
public class GetPaginatedTodoListsUseCaseTest {

    @Mock
    private ITodoRepositoryAsync todoRepositoryAsyncMock;

    @Mock
    private TodoMapper todoMapperMock;

    @InjectMocks
    private GetPaginatedTodoListsUseCase useCase;

    private LogCaptor logCaptor;

    @BeforeEach
    public void setup() {

        logCaptor = LogCaptor.forClass(GetPaginatedTodoListsUseCase.class);
    }

    @DisplayName("Should execute successfully")
    @Test
    public void shouldExecuteSuccessfully() {

        // Arrange
        int pageNumber = 1;
        int pageSize = 10;
        int maxPageSize = 20;
        int defaultPageSize = 10;
        int initialPagination = 1;

        var request = new GetPaginatedTodoListsUseCaseRequest(pageNumber, pageSize, maxPageSize, defaultPageSize,
                initialPagination);

        var todos = List.of(mock(Todo.class), mock(Todo.class));

        when(todoRepositoryAsyncMock.getPaginatedTodoListsAsync(pageSize, pageNumber))
                .thenReturn(CompletableFuture.completedFuture(todos));

        when(todoRepositoryAsyncMock.getTotalRecordsAsync())
                .thenReturn(CompletableFuture.completedFuture(todos.size()));

        when(todoMapperMock.convertTodoUseCaseResponse(any(Todo.class)))
                .thenReturn(mock(TodoUseCaseResponse.class));

        // Act
        var useCaseResponse = useCase.runAsync(request).join();

        // Assert
        assertNotNull(useCaseResponse);

        assertEquals(1, useCaseResponse.getPageNumber());
        assertEquals(10, useCaseResponse.getPageSize());
        assertEquals(1, useCaseResponse.getTotalPages());
        assertEquals(2, useCaseResponse.getTotalRecords());
        assertEquals(2, useCaseResponse.getTodoListUseCaseResponse().size());

        verify(todoRepositoryAsyncMock, times(1)).getPaginatedTodoListsAsync(pageSize, pageNumber);
        verify(todoRepositoryAsyncMock, times(1)).getTotalRecordsAsync();

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start useCase GetPaginatedTodoListsUseCase > method runAsync.",
                        "Finishes successfully useCase GetPaginatedTodoListsUseCase > method runAsync.");
    }
}