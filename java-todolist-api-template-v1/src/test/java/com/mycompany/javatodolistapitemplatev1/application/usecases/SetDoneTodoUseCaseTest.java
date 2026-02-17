package com.mycompany.javatodolistapitemplatev1.application.usecases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.SetDoneTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
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
public class SetDoneTodoUseCaseTest {

    @Mock
    private ITodoRepositoryAsync todoRepositoryAsyncMock;

    @Mock
    private IGetTodoUseCase getTodoUseCaseMock;

    @InjectMocks
    private SetDoneTodoUseCase useCase;

    private LogCaptor logCaptor;

    @BeforeEach
    public void setUp() {

        logCaptor = LogCaptor.forClass(SetDoneTodoUseCase.class);
    }

    @ParameterizedTest
    @CsvSource({ "1, Title 1, true",
            "2, Title 2, false",
            "3, Title 3, true"
    })
    public void shouldExecuteSuccessfully(long id, String title, boolean done) {

        // Arrange
        var useCaseRequest = new SetDoneTodoUseCaseRequest(id, done);

        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(id);
        todoUseCaseResponse.setTitle(title);
        todoUseCaseResponse.setDone(done);

        when(getTodoUseCaseMock.runAsync(any(long.class)))
                .thenReturn(CompletableFuture.completedFuture(todoUseCaseResponse));

        when(todoRepositoryAsyncMock.updateAsync(any(Todo.class)))
                .thenReturn(CompletableFuture.completedFuture(true));

        // Act
        var useCaseResponse = useCase.runAsync(useCaseRequest).join();

        // Assert
        assertThat(useCaseResponse).isNotNull();
        assertThat(useCaseResponse).isTrue();

        assertThat(useCase.hasErrorNotification()).isFalse();
        assertThat(useCase.getErrorNotifications()).isEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(0);

        verify(getTodoUseCaseMock, times(1)).runAsync(any(long.class));
        verify(todoRepositoryAsyncMock, times(1)).updateAsync(any(Todo.class));

        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start useCase SetDoneTodoUseCase > method runAsync.",
                "Finishes successfully useCase SetDoneTodoUseCase > method runAsync.");

    }
}