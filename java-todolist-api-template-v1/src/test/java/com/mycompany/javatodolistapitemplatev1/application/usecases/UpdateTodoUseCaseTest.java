package com.mycompany.javatodolistapitemplatev1.application.usecases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.UpdateTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdateTodoUseCaseTest {

    @Mock
    private ITodoRepositoryAsync todoRepositoryAsyncMock;

    @Mock
    private IGetTodoUseCase getTodoUseCaseMock;

    @InjectMocks
    private UpdateTodoUseCase useCase;

    private LogCaptor logCaptor;

    @BeforeEach
    public void setUp() {
        logCaptor = LogCaptor.forClass(UpdateTodoUseCase.class);
    }

    @DisplayName("Should execute successfully when updating a todo")
    @ParameterizedTest
    @CsvSource({
            "1, Old Title, New Title, false",
            "2, Old Title 2, New Title 2, true",
            "3, Old Title 3, New Title 3, false"
    })
    public void shouldExecuteSuccessfully(long id, String oldTitle, String newTitle, boolean done) {

        // Arrange
        var updateRequest = new UpdateTodoUseCaseRequest(id, newTitle, done);

        var existingTodoResponse = new TodoUseCaseResponse();
        existingTodoResponse.setId(id);
        existingTodoResponse.setTitle(oldTitle);
        existingTodoResponse.setDone(!done);

        when(getTodoUseCaseMock.runAsync(id))
                .thenReturn(CompletableFuture.completedFuture(existingTodoResponse));

        when(todoRepositoryAsyncMock.updateAsync(any(Todo.class)))
                .thenReturn(CompletableFuture.completedFuture(true));

        // Act
        var result = useCase.runAsync(updateRequest).join();

        // Assert
        assertTrue(result);

        assertThat(useCase.hasErrorNotification()).isFalse();
        assertThat(useCase.getErrorNotifications()).isEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(0);

        verify(getTodoUseCaseMock, times(1)).runAsync(id);
        verify(todoRepositoryAsyncMock, times(1)).updateAsync(any(Todo.class));

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start useCase UpdateTodoUseCase > method runAsync.",
                        "Finishes successfully useCase UpdateTodoUseCase > method runAsync.");
    }

    @DisplayName("Should return false when request has error notification")
    @Test
    public void shouldReturnFalseWhenRequestHasErrorNotification() {

        // Arrange
        var invalidRequest = new UpdateTodoUseCaseRequest(1, null, false);

        // Act
        var result = useCase.runAsync(invalidRequest).join();

        // Assert
        assertFalse(result);

        assertThat(useCase.hasErrorNotification()).isTrue();
        assertThat(useCase.getErrorNotifications()).isNotEmpty();

        verify(getTodoUseCaseMock, never()).runAsync(any());
        verify(todoRepositoryAsyncMock, never()).updateAsync(any());

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Start useCase UpdateTodoUseCase > method runAsync.")
                .doesNotContain("Finishes successfully useCase UpdateTodoUseCase > method runAsync");
    }

    @DisplayName("Should return false when getTodoUseCase has error notification")
    @Test
    public void shouldReturnFalseWhenGetTodoUseCaseHasErrorNotification() {

        // Arrange
        var updateRequest = new UpdateTodoUseCaseRequest(1L, "New Title", false);

        when(getTodoUseCaseMock.runAsync(1L))
                .thenReturn(CompletableFuture.completedFuture(null));

        when(getTodoUseCaseMock.hasErrorNotification()).thenReturn(true);
        when(getTodoUseCaseMock.getErrorNotifications()).thenReturn(java.util.List.of(
                new com.mycompany.javatodolistapitemplatev1.shared.notification.models.NotificationMessage("ERR001", "Todo not found")));

        // Act
        var result = useCase.runAsync(updateRequest).join();

        // Assert
        assertFalse(result);

        assertThat(useCase.hasErrorNotification()).isTrue();
        assertThat(useCase.getErrorNotifications()).isNotEmpty();

        verify(getTodoUseCaseMock, times(1)).runAsync(1L);
        verify(todoRepositoryAsyncMock, never()).updateAsync(any());

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Start useCase UpdateTodoUseCase > method runAsync.")
                .doesNotContain("Finishes successfully useCase UpdateTodoUseCase > method runAsync");
    }

    @DisplayName("Should return false when update fails")
    @ParameterizedTest
    @CsvSource({
            "1, Old Title, New Title, false",
            "2, Old Title 2, New Title 2, true"
    })
    public void shouldReturnFalseWhenUpdateFails(long id, String oldTitle, String newTitle, boolean done) {

        // Arrange
        var updateRequest = new UpdateTodoUseCaseRequest(id, newTitle, done);

        var existingTodoResponse = new TodoUseCaseResponse();
        existingTodoResponse.setId(id);
        existingTodoResponse.setTitle(oldTitle);
        existingTodoResponse.setDone(!done);

        when(getTodoUseCaseMock.runAsync(any(long.class)))
                .thenReturn(CompletableFuture.completedFuture(existingTodoResponse));

        when(getTodoUseCaseMock.hasErrorNotification()).thenReturn(false);

        when(todoRepositoryAsyncMock.updateAsync(any(Todo.class)))
                .thenReturn(CompletableFuture.completedFuture(false));

        // Act
        var result = useCase.runAsync(updateRequest).join();

        // Assert
        assertFalse(result);

        assertThat(useCase.hasErrorNotification()).isTrue();
        assertThat(useCase.getErrorNotifications()).isNotEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(1);

        assertThat(useCase.getErrorNotifications().getFirst().getKey())
                .isEqualTo(MsgUltil.FAILED_TO_UPDATE_X0(null)[0]);
        assertThat(useCase.getErrorNotifications().getFirst().getMessage())
                .isEqualTo(MsgUltil.FAILED_TO_UPDATE_X0("Todo")[1]);

        verify(getTodoUseCaseMock, times(1)).runAsync(any(long.class));
        verify(todoRepositoryAsyncMock, times(1)).updateAsync(any(Todo.class));

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Start useCase UpdateTodoUseCase > method runAsync.")
                .doesNotContain("Finishes successfully useCase UpdateTodoUseCase > method runAsync");
    }
}