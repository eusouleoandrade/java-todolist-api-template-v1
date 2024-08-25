package com.mycompany.javatodolistapitemplatev1.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.mappers.CreateTodoUseCaseRequestMapper;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

import nl.altindag.log.LogCaptor;

@SpringBootTest
public class CreateTodoUseCaseTest {

    @Mock
    private ITodoRepositoryAsync todoRepositoryAsyncMock;

    @Mock
    private CreateTodoUseCaseRequestMapper createTodoUseCaseRequestMapperMock;

    @Mock
    private TodoMapper todoMapperMock;

    @InjectMocks
    private CreateTodoUseCase useCase;

    private LogCaptor logCaptor;

    @BeforeEach
    public void setup() {

        logCaptor = LogCaptor.forClass(CreateTodoUseCase.class);
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
        var request = new CreateTodoUseCaseRequest(title);

        var todo = new Todo(id, title, done);

        var expectedResponse = new TodoUseCaseResponse() {
            {
                setId(id);
                setTitle(title);
                setDone(done);
            }
        };

        when(createTodoUseCaseRequestMapperMock.convertTodo(request)).thenReturn(todo);
        when(todoRepositoryAsyncMock.createAsync(todo)).thenReturn(CompletableFuture.completedFuture(todo));
        when(todoMapperMock.convertTodoUseCaseResponse(todo)).thenReturn(expectedResponse);

        // Act
        var response = useCase.runAsync(request).join();

        // Assert
        assertNotNull(response);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);

        assertThat(useCase.hasErrorNotification()).isFalse();
        assertThat(useCase.getErrorNotifications()).isEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(0);

        verify(createTodoUseCaseRequestMapperMock, times(1)).convertTodo(request);
        verify(todoRepositoryAsyncMock, times(1)).createAsync(todo);
        verify(todoMapperMock, times(1)).convertTodoUseCaseResponse(todo);

        assertThat(logCaptor.getInfoLogs())
                .containsExactly(
                        "Start useCase CreateTodoUseCase > method runAsync.",
                        "Finishes successfully useCase CreateTodoUseCase > method runAsync.");
    }

    @DisplayName("Test RunAsync failure when request has error notification")
    @Test
    public void testRunAsyncFailureWhenRequestHasErrorNotification() {

        String[] titles = { null, "", " " };

        for (var title : titles) {

            // Arranje
            var useCaseRequest = new CreateTodoUseCaseRequest(title);

            useCase = new CreateTodoUseCase(todoRepositoryAsyncMock, createTodoUseCaseRequestMapperMock,
                    todoMapperMock);

            logCaptor = LogCaptor.forClass(CreateTodoUseCase.class);

            // Act
            var useCaseResponse = useCase.runAsync(useCaseRequest).join();

            // Assert
            assertNull(useCaseResponse);

            assertThat(useCase.hasErrorNotification()).isTrue();

            assertThat(useCase.getErrorNotifications()).isNotEmpty();
            assertThat(useCase.getErrorNotifications()).hasSize(1);

            assertThat(useCase.getErrorNotifications().get(0).getKey())
                    .isEqualTo(MsgUltil.X0_IS_REQUIRED(null)[0]);
            assertThat(useCase.getErrorNotifications().get(0).getMessage())
                    .isEqualTo(MsgUltil.X0_IS_REQUIRED("Title")[1]);

            assertThat(useCase.getSuccessNotifications()).isEmpty();

            assertThat(logCaptor.getInfoLogs())
                    .containsExactly("Start useCase CreateTodoUseCase > method runAsync.")
                    .doesNotContain("Finishes successfully useCase CreateTodoUseCase > method runAsync.");
        }
    }

    @DisplayName("Test RunAsync failure when repository response is null")
    @ParameterizedTest
    @CsvSource({
            "1, Title 1., false",
            "2, Title 2., false",
            "3, Title 3., false"
    })
    public void testRunAsyncFailureWhenRepositoResponseIsNull(long id, String title, boolean done) {

        // Arranje
        var request = new CreateTodoUseCaseRequest(title);
        var todo = new Todo(id, title, done);

        when(createTodoUseCaseRequestMapperMock.convertTodo(request)).thenReturn(todo);
        when(todoRepositoryAsyncMock.createAsync(todo)).thenReturn(CompletableFuture.completedFuture(null));

        useCase = new CreateTodoUseCase(todoRepositoryAsyncMock, createTodoUseCaseRequestMapperMock,
                todoMapperMock);

        // Act
        var useCaseResponse = useCase.runAsync(request).join();

        // Assert
        assertNull(useCaseResponse);

        assertThat(useCase.hasErrorNotification()).isTrue();

        assertThat(useCase.getErrorNotifications()).isNotEmpty();
        assertThat(useCase.getErrorNotifications()).hasSize(1);

        assertThat(useCase.getErrorNotifications().get(0).getKey())
                .isEqualTo(MsgUltil.OBJECT_X0_IS_NULL(null)[0]);
        assertThat(useCase.getErrorNotifications().get(0).getMessage())
                .isEqualTo(MsgUltil.OBJECT_X0_IS_NULL("Todo repository response")[1]);

        assertThat(useCase.getSuccessNotifications()).isEmpty();

        assertThat(logCaptor.getInfoLogs())
                .containsExactly("Start useCase CreateTodoUseCase > method runAsync.")
                .doesNotContain("Finishes successfully useCase CreateTodoUseCase > method runAsync.");
    }
}