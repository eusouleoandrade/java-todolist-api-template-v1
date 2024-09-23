package com.mycompany.javatodolistapitemplatev1.application.usecases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;
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

import java.util.Random;
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
    @CsvSource({"1, Title 1, false",
            "2, Title 2, true",
            "3, Title 3, false"
    })
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

        assertThat(logCaptor.getInfoLogs()).containsExactly(
                "Start useCase GetTodoUseCase > method runAsync.",
                "Finishes successfully useCase GetTodoUseCase > method runAsync.");
    }

    @DisplayName("Should not execute when id is invalid")
    @ParameterizedTest
    @CsvSource({
            "0",
            "-1"
    })
    public void shouldNotExecuteWhenIdIsInvalid(long id) {

        // Arranje

        // Act
        var useCaseResponse = useCase.runAsync(id).join();

        // Assert
        assertThat(useCaseResponse).isNull();

        assertThat(useCase.hasErrorNotification()).isTrue();

        assertThat(useCase.getErrorNotifications()).isNotEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(1);
        assertThat(useCase.getErrorNotifications().get(0).getKey()).isEqualTo(MsgUltil.IDENTIFIER_X0_IS_INVALID(null)[0]);
        assertThat(useCase.getErrorNotifications().get(0).getMessage()).isEqualTo(MsgUltil.IDENTIFIER_X0_IS_INVALID(Long.toString(id))[1]);

        assertThat(useCase.getSuccessNotifications()).isEmpty();

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Start useCase GetTodoUseCase > method runAsync.")
                .doesNotContain("Finishes successfully useCase GetTodoUseCase > method runAsync.");
    }

    @DisplayName("Should not execute when todo is null")
    @Test
    public void shouldNotExecuteWhenTodoIsNull(){

        // Arranje
        long id = new Random().nextLong(1,100);

        when(todoRepositoryAsyncMock.getAsync(any(Long.class))).thenReturn(CompletableFuture.completedFuture(null));

        // Act
        var useCaseResponse = useCase.runAsync(id).join();

        // Assert
        assertThat(useCaseResponse).isNull();

        assertThat(useCase.hasErrorNotification()).isTrue();

        assertThat(useCase.getErrorNotifications()).isNotEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(1);
        assertThat(useCase.getErrorNotifications().get(0).getKey()).isEqualTo(MsgUltil.DATA_OF_X0_X1_NOT_FOUND(null, null)[0]);
        assertThat(useCase.getErrorNotifications().get(0).getMessage()).isEqualTo(MsgUltil.DATA_OF_X0_X1_NOT_FOUND("Todo", Long.toString(id))[1]);

        assertThat(useCase.getSuccessNotifications()).isEmpty();

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Start useCase GetTodoUseCase > method runAsync.")
                .doesNotContain("Finishes successfully useCase GetTodoUseCase > method runAsync.");
    }
}