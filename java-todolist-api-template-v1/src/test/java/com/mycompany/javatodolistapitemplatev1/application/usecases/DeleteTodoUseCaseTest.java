package com.mycompany.javatodolistapitemplatev1.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

import nl.altindag.log.LogCaptor;

@SpringBootTest
public class DeleteTodoUseCaseTest {

    @Mock
    private ITodoRepositoryAsync todoRepositoryAsyncMock;

    @Mock
    private IGetTodoUseCase getTodoUseCaseMock;

    @InjectMocks
    private DeleteTodoUseCase useCase;

    private LogCaptor logCaptor;

    @BeforeEach
    public void setup() {

        logCaptor = LogCaptor.forClass(DeleteTodoUseCase.class);
    }

    @DisplayName("Should execute successfully")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., false",
            "2, Title 2., false",
            "3, Title 3., false"
    })
    public void shouldExecuteSucessfully(long id, String title, boolean done) {

        // Arranje
        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(id);
        todoUseCaseResponse.setTitle(title);
        todoUseCaseResponse.setDone(done);

        when(todoRepositoryAsyncMock.deleteAsync(id)).thenReturn(CompletableFuture.completedFuture(true));
        when(getTodoUseCaseMock.runAsync(id)).thenReturn(CompletableFuture.completedFuture(todoUseCaseResponse));

        // Act
        var useCaseResponse = useCase.runAsync(id).join();

        // Assert
        assertTrue(useCaseResponse);

        assertThat(useCase.hasErrorNotification()).isFalse();
        assertThat(useCase.getErrorNotifications()).isEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(0);

        verify(todoRepositoryAsyncMock, times(1)).deleteAsync(id);
        verify(getTodoUseCaseMock, times(1)).runAsync(id);

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start useCase DeleteTodoUseCase > method runAsync.",
                        "Finishes successfully useCase DeleteTodoUseCase > method runAsync.");
    }

    @DisplayName("Test run async failure when not find todo")
    @ParameterizedTest
    @CsvSource({
            "1",
            "2",
            "3"
    })
    public void testRunAsyncFailureWhenNotFindTodo(long id) {

        // Arranje
        when(todoRepositoryAsyncMock.getAsync(id)).thenReturn(CompletableFuture.completedFuture(null));

        var todoMapperMock = mock(TodoMapper.class);
        var getTodoUseCase = new GetTodoUseCase(todoRepositoryAsyncMock, todoMapperMock);

        useCase = new DeleteTodoUseCase(todoRepositoryAsyncMock, getTodoUseCase);

        // Act
        var useCaseResponse = useCase.runAsync(id).join();

        // Assert
        assertFalse(useCaseResponse);

        assertThat(useCase.hasErrorNotification()).isTrue();

        assertThat(useCase.getErrorNotifications()).isNotEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(1);

        assertThat(useCase.getErrorNotifications().get(0).getKey())
                .isEqualTo(MsgUltil.DATA_OF_X0_X1_NOT_FOUND(null, null)[0]);
        assertThat(useCase.getErrorNotifications().get(0).getMessage())
                .isEqualTo(MsgUltil.DATA_OF_X0_X1_NOT_FOUND("Todo", Long.toString(id))[1]);

        assertThat(useCase.getSuccessNotifications()).isEmpty();

        verify(todoRepositoryAsyncMock, times(1)).getAsync(id);

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Start useCase DeleteTodoUseCase > method runAsync.")
                .doesNotContain("Finishes successfully useCase DeleteTodoUseCase > method runAsync.");
    }

    @DisplayName("Test run async failure when failed do remove")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., false",
            "2, Title 2., false",
            "3, Title 3., false"
    })
    public void testRunAsyncFailureWhenFailedToRemove(long id, String title, boolean done) {

        // Arranje
        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(id);
        todoUseCaseResponse.setTitle(title);
        todoUseCaseResponse.setDone(done);

        when(todoRepositoryAsyncMock.deleteAsync(id)).thenReturn(CompletableFuture.completedFuture(false));
        when(getTodoUseCaseMock.runAsync(id)).thenReturn(CompletableFuture.completedFuture(todoUseCaseResponse));

        // Act
        var useCaseResponse = useCase.runAsync(id).join();

        // Assert
        assertFalse(useCaseResponse);

        assertThat(useCase.hasErrorNotification()).isTrue();

        assertThat(useCase.getErrorNotifications()).isNotEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(1);

        assertThat(useCase.getErrorNotifications().get(0).getKey())
                .isEqualTo(MsgUltil.FAILED_TO_REMOVE_X0(null)[0]);
        assertThat(useCase.getErrorNotifications().get(0).getMessage())
                .isEqualTo(MsgUltil.FAILED_TO_REMOVE_X0("Todo")[1]);

        assertThat(useCase.getSuccessNotifications()).isEmpty();

        verify(todoRepositoryAsyncMock, times(1)).deleteAsync(id);
        verify(getTodoUseCaseMock, times(1)).runAsync(id);

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Start useCase DeleteTodoUseCase > method runAsync.")
                .doesNotContain("Finishes successfully useCase DeleteTodoUseCase > method runAsync.");
    }
}