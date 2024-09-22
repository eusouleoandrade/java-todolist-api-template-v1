package com.mycompany.javatodolistapitemplatev1.application.usecases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GetTodoUseCaseTest {

    @Mock
    private ITodoRepositoryAsync todoRepositoryAsyncMock;

    @Mock
    private TodoMapper todoMapperMock;

    @InjectMocks
    private GetTodoUseCase useCase;

    private LogCaptor logCaptor;

    @BeforeEach
    public void setUp() {
        logCaptor = LogCaptor.forClass(GetTodoUseCase.class);
    }

    @DisplayName("Should execute successfully")
    @ParameterizedTest
    @CsvSource({"1, Title 1, false", "2, Title 2, true", "3, Title 3, false"})
    public void shouldExecuteSuccessfully(long id, String title, boolean done) {

        // Arranje
        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(id);
        todoUseCaseResponse.setTitle(title);
        todoUseCaseResponse.setDone(done);

        when(todoRepositoryAsyncMock.getAsync(any(long.class))).thenReturn(CompletableFuture.completedFuture(mock(Todo.class)));
        when(todoMapperMock.convertTodoUseCaseResponse(any(Todo.class))).thenReturn(todoUseCaseResponse);

        // Act
        var useCaseResponse = useCase.runAsync(id).join();

        // Assert
        assertThat(useCaseResponse).isNotNull();

        assertThat(useCaseResponse.getId()).isEqualTo(id);
        assertThat(useCaseResponse.getTitle()).isEqualTo(title);
        assertThat(useCaseResponse.isDone()).isEqualTo(done);

        assertThat(useCase.hasErrorNotification()).isFalse();
        assertThat(useCase.getErrorNotifications()).isEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(0);

        verify(todoRepositoryAsyncMock, times(1)).getAsync(id);
        verify(todoMapperMock, times(1)).convertTodoUseCaseResponse(any(Todo.class));

        assertThat(logCaptor.getInfoLogs()).containsExactly("Start useCase GetTodoUseCase > method runAsync.", "Finishes successfully useCase GetTodoUseCase > method runAsync.");
    }

    // TODO: Continuar dos demais cenários de testes para esse useCase
}