package com.mycompany.javatodolistapitemplatev1.application.usecases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GetTodoListUseCaseTest {

    @Mock
    private ITodoRepositoryAsync todoRepositoryAsyncMock;

    @Mock
    private TodoMapper todoMapperMock;

    @InjectMocks
    private GetTodoListUseCase useCase;

    private LogCaptor logCaptor;

    @BeforeEach
    public void setUp() {

        logCaptor = LogCaptor.forClass(GetTodoListUseCase.class);
    }

    @DisplayName("Should execute successfully")
    @Test
    public void shouldExecuteSuccessfully() {

        // Arranje
        var todos = List.of(mock(Todo.class), mock(Todo.class));

        when(todoRepositoryAsyncMock.getTodoListAsync()).thenReturn(CompletableFuture.completedFuture(todos));

        when(todoMapperMock.convertGetTodoListUseCaseResponse(any(Todo.class))).thenReturn(mock(GetTodoListUseCaseResponse.class));

        // Act
        var useCaseResponse = useCase.runAsync().join();

        // Assert
        assertNotNull(useCaseResponse);

        assertEquals(2, useCaseResponse.size());

        verify(todoRepositoryAsyncMock, times(1)).getTodoListAsync();
        verify(todoMapperMock, times(2)).convertGetTodoListUseCaseResponse(any(Todo.class));

        assertThat(logCaptor.getInfoLogs()).containsExactly("Start useCase GetTodoListUseCase > method runAsync.", "Finishes successfully useCase GetTodoListUseCase > method runAsync.");
    }
}