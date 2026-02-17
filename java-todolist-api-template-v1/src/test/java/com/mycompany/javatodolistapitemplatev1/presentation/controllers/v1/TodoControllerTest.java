package com.mycompany.javatodolistapitemplatev1.presentation.controllers.v1;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.GetPaginatedTodoListsUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.SetDoneTodoRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.SetDoneTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.UpdateTodoRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.UpdateTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.CreateTodoResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetPaginatedTodoListsUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListPagedResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.SetDoneTodoResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.UpdateTodoResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.ICreateTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IDeleteTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetPaginatedTodoListsUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoListUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.ISetDoneTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IUpdateTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.CreateTodoRequestMapper;
import com.mycompany.javatodolistapitemplatev1.application.mappers.GetTodoListUseCaseResponseMapper;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoUseCaseResponseMapper;
import com.mycompany.javatodolistapitemplatev1.shared.notification.contexts.NotificationContext;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("TodoController Tests")
public class TodoControllerTest {

    @Mock
    private IGetTodoListUseCase getTodoListUseCaseMock;

    @Mock
    private IGetPaginatedTodoListsUseCase getPaginatedTodoListsUseCaseMock;

    @Mock
    private IGetTodoUseCase getTodoUseCaseMock;

    @Mock
    private GetTodoListUseCaseResponseMapper getTodoListUseCaseResponseMapperMock;

    @Mock
    private TodoUseCaseResponseMapper todoUseCaseResponseMapperMock;

    @Mock
    private NotificationContext notificationContextMock;

    @Mock
    private ICreateTodoUseCase createTodoUseCaseMock;

    @Mock
    private CreateTodoRequestMapper createTodoRequestMapperMock;

    @Mock
    private IDeleteTodoUseCase deleteTodoUseCaseMock;

    @Mock
    private IUpdateTodoUseCase updateTodoUseCaseMock;

    @Mock
    private ISetDoneTodoUseCase setDoneTodoUseCaseMock;

    @InjectMocks
    private TodoController todoController;

    private LogCaptor logCaptor;

    @BeforeEach
    public void setUp() {
        logCaptor = LogCaptor.forClass(TodoController.class);
        logCaptor.setLogLevelToInfo();

        // Configure pagination properties
        ReflectionTestUtils.setField(todoController, "maxPageSize", 100);
        ReflectionTestUtils.setField(todoController, "defaultPageSize", 10);
        ReflectionTestUtils.setField(todoController, "initialPagination", 1);
    }

    // ==================== getAll() Tests ====================

    @Test
    @DisplayName("getAll - should return all todos successfully")
    public void testGetAll_Success() {
        // Arrange
        var todoUseCaseResponse1 = new GetTodoListUseCaseResponse();
        todoUseCaseResponse1.setId(1L);
        todoUseCaseResponse1.setTitle("Todo 1");
        todoUseCaseResponse1.setDone(false);

        var todoUseCaseResponse2 = new GetTodoListUseCaseResponse();
        todoUseCaseResponse2.setId(2L);
        todoUseCaseResponse2.setTitle("Todo 2");
        todoUseCaseResponse2.setDone(true);

        List<GetTodoListUseCaseResponse> useCaseResponses = List.of(todoUseCaseResponse1, todoUseCaseResponse2);

        var todoQuery1 = mock(TodoQuery.class);
        var todoQuery2 = mock(TodoQuery.class);

        when(getTodoListUseCaseMock.runAsync()).thenReturn(CompletableFuture.completedFuture(useCaseResponses));
        when(getTodoListUseCaseResponseMapperMock.convertTodoQuery(any())).thenReturn(todoQuery1, todoQuery2);

        // Act
        ResponseEntity<GetTodoListResponse> response = todoController.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTodos().size());

        verify(getTodoListUseCaseMock, times(1)).runAsync();
        verify(getTodoListUseCaseResponseMapperMock, times(2)).convertTodoQuery(any());

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method getAll.",
                        "Finishes successfully controller TodoController > method getAll.");
    }

    @Test
    @DisplayName("getAll - should return empty list successfully")
    public void testGetAll_EmptyList() {
        // Arrange
        when(getTodoListUseCaseMock.runAsync()).thenReturn(CompletableFuture.completedFuture(Collections.emptyList()));

        // Act
        ResponseEntity<GetTodoListResponse> response = todoController.getAll();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getTodos().size());

        verify(getTodoListUseCaseMock, times(1)).runAsync();

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method getAll.",
                        "Finishes successfully controller TodoController > method getAll.");
    }

    // ==================== getPaginated() Tests ====================

    @Test
    @DisplayName("getPaginated - should return paginated todos successfully")
    public void testGetPaginated_Success() {
        // Arrange
        int pageNumber = 1;
        int pageSize = 10;

        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(1L);
        todoUseCaseResponse.setTitle("Todo 1");
        todoUseCaseResponse.setDone(false);

        var paginatedResponse = new GetPaginatedTodoListsUseCaseResponse(1, 10, 1, 1, List.of(todoUseCaseResponse));

        var todoQuery = mock(TodoQuery.class);

        when(getPaginatedTodoListsUseCaseMock.runAsync(any(GetPaginatedTodoListsUseCaseRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(paginatedResponse));
        when(todoUseCaseResponseMapperMock.convertTodoQuery(any())).thenReturn(todoQuery);

        // Act
        ResponseEntity<GetTodoListPagedResponse> response = todoController.getPaginated(pageNumber, pageSize);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTodos().size());
        assertEquals(1, response.getBody().getPageNumber());
        assertEquals(10, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(1, response.getBody().getTotalRecords());

        verify(getPaginatedTodoListsUseCaseMock, times(1)).runAsync(any(GetPaginatedTodoListsUseCaseRequest.class));
        verify(todoUseCaseResponseMapperMock, times(1)).convertTodoQuery(any());

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method getPaginated.",
                        "Finishes successfully controller TodoController > method getPaginated.");
    }

    @Test
    @DisplayName("getPaginated - should return empty paginated list successfully")
    public void testGetPaginated_EmptyPage() {
        // Arrange
        int pageNumber = 2;
        int pageSize = 10;

        var paginatedResponse = new GetPaginatedTodoListsUseCaseResponse(2, 10, 1, 0, Collections.emptyList());

        when(getPaginatedTodoListsUseCaseMock.runAsync(any(GetPaginatedTodoListsUseCaseRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(paginatedResponse));

        // Act
        ResponseEntity<GetTodoListPagedResponse> response = todoController.getPaginated(pageNumber, pageSize);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getTodos().size());
        assertEquals(0, response.getBody().getTotalRecords());

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method getPaginated.",
                        "Finishes successfully controller TodoController > method getPaginated.");
    }

    // ==================== get(id) Tests ====================

    @Test
    @DisplayName("get - should return todo by id successfully")
    public void testGet_Success() {
        // Arrange
        long todoId = 1L;
        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(todoId);
        todoUseCaseResponse.setTitle("Todo 1");
        todoUseCaseResponse.setDone(false);

        var todoQuery = mock(TodoQuery.class);

        when(getTodoUseCaseMock.runAsync(todoId)).thenReturn(CompletableFuture.completedFuture(todoUseCaseResponse));
        when(getTodoUseCaseMock.hasErrorNotification()).thenReturn(false);
        when(todoUseCaseResponseMapperMock.convertTodoQuery(any())).thenReturn(todoQuery);

        // Act
        ResponseEntity<?> response = todoController.get(todoId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(GetTodoResponse.class, response.getBody());

        verify(getTodoUseCaseMock, times(1)).runAsync(todoId);
        verify(getTodoUseCaseMock, times(1)).hasErrorNotification();
        verify(todoUseCaseResponseMapperMock, times(1)).convertTodoQuery(any());

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method get.",
                        "Finishes successfully controller TodoController > method get.");
    }

    @Test
    @DisplayName("get - should return bad request when todo not found")
    public void testGet_NotFound() {
        // Arrange
        long todoId = 999L;

        when(getTodoUseCaseMock.runAsync(todoId)).thenReturn(CompletableFuture.completedFuture(null));
        when(getTodoUseCaseMock.hasErrorNotification()).thenReturn(true);

        // Act
        ResponseEntity<?> response = todoController.get(todoId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(getTodoUseCaseMock, times(1)).runAsync(todoId);
        verify(getTodoUseCaseMock, times(1)).hasErrorNotification();
        verify(notificationContextMock, times(1)).addErrorNotifications(getTodoUseCaseMock);

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method get.");
    }

    // ==================== post() Tests ====================

    @Test
    @DisplayName("post - should create todo successfully")
    public void testPost_Success() {
        // Arrange
        var createRequest = new CreateTodoRequest();
        createRequest.setTitle("New Todo");

        var useCaseRequest = new CreateTodoUseCaseRequest("New Todo");

        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(1L);
        todoUseCaseResponse.setTitle("New Todo");
        todoUseCaseResponse.setDone(false);

        var todoQuery = mock(TodoQuery.class);

        when(createTodoRequestMapperMock.convertCreateTodoUseCaseRequest(any())).thenReturn(useCaseRequest);
        when(createTodoUseCaseMock.runAsync(any())).thenReturn(CompletableFuture.completedFuture(todoUseCaseResponse));
        when(createTodoUseCaseMock.hasErrorNotification()).thenReturn(false);
        when(todoUseCaseResponseMapperMock.convertTodoQuery(any())).thenReturn(todoQuery);

        // Act
        ResponseEntity<?> response = todoController.post(createRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(CreateTodoResponse.class, response.getBody());

        verify(createTodoRequestMapperMock, times(1)).convertCreateTodoUseCaseRequest(any());
        verify(createTodoUseCaseMock, times(1)).runAsync(any());
        verify(createTodoUseCaseMock, times(1)).hasErrorNotification();
        verify(todoUseCaseResponseMapperMock, times(1)).convertTodoQuery(any());

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method post.",
                        "Finishes successfully controller TodoController > method post.");
    }

    @Test
    @DisplayName("post - should return bad request when validation fails")
    public void testPost_ValidationError() {
        // Arrange
        var createRequest = new CreateTodoRequest();
        createRequest.setTitle("");

        var useCaseRequest = new CreateTodoUseCaseRequest("");

        when(createTodoRequestMapperMock.convertCreateTodoUseCaseRequest(any())).thenReturn(useCaseRequest);
        when(createTodoUseCaseMock.runAsync(any())).thenReturn(CompletableFuture.completedFuture(null));
        when(createTodoUseCaseMock.hasErrorNotification()).thenReturn(true);

        // Act
        ResponseEntity<?> response = todoController.post(createRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(createTodoRequestMapperMock, times(1)).convertCreateTodoUseCaseRequest(any());
        verify(createTodoUseCaseMock, times(1)).runAsync(any());
        verify(createTodoUseCaseMock, times(1)).hasErrorNotification();
        verify(notificationContextMock, times(1)).addErrorNotifications(createTodoUseCaseMock);

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method post.");
    }

    // ==================== delete(id) Tests ====================

    @Test
    @DisplayName("delete - should delete todo successfully")
    public void testDelete_Success() {
        // Arrange
        long todoId = 1L;

        when(deleteTodoUseCaseMock.runAsync(todoId)).thenReturn(CompletableFuture.completedFuture(null));
        when(deleteTodoUseCaseMock.hasErrorNotification()).thenReturn(false);

        // Act
        ResponseEntity<?> response = todoController.delete(todoId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(deleteTodoUseCaseMock, times(1)).runAsync(todoId);
        verify(deleteTodoUseCaseMock, times(1)).hasErrorNotification();

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method delete.",
                        "Finishes successfully controller TodoController > method delete.");
    }

    @Test
    @DisplayName("delete - should return bad request when todo not found")
    public void testDelete_NotFound() {
        // Arrange
        long todoId = 999L;

        when(deleteTodoUseCaseMock.runAsync(todoId)).thenReturn(CompletableFuture.completedFuture(null));
        when(deleteTodoUseCaseMock.hasErrorNotification()).thenReturn(true);

        // Act
        ResponseEntity<?> response = todoController.delete(todoId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(deleteTodoUseCaseMock, times(1)).runAsync(todoId);
        verify(deleteTodoUseCaseMock, times(1)).hasErrorNotification();
        verify(notificationContextMock, times(1)).addErrorNotifications(deleteTodoUseCaseMock);

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method delete.");
    }

    // ==================== put(id) Tests ====================

    @Test
    @DisplayName("put - should update todo successfully")
    public void testPut_Success() {
        // Arrange
        long todoId = 1L;
        var updateRequest = new UpdateTodoRequest();
        updateRequest.setTitle("Updated Todo");
        updateRequest.setDone(true);

        when(updateTodoUseCaseMock.runAsync(any(UpdateTodoUseCaseRequest.class))).thenReturn(CompletableFuture.completedFuture(null));
        when(updateTodoUseCaseMock.hasErrorNotification()).thenReturn(false);

        // Act
        ResponseEntity<?> response = todoController.put(todoId, updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(UpdateTodoResponse.class, response.getBody());

        verify(updateTodoUseCaseMock, times(1)).runAsync(any(UpdateTodoUseCaseRequest.class));
        verify(updateTodoUseCaseMock, times(1)).hasErrorNotification();

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method put.",
                        "Finishes successfully controller TodoController > method put.");
    }

    @Test
    @DisplayName("put - should return bad request when todo not found")
    public void testPut_NotFound() {
        // Arrange
        long todoId = 999L;
        var updateRequest = new UpdateTodoRequest();
        updateRequest.setTitle("Updated Todo");
        updateRequest.setDone(true);

        when(updateTodoUseCaseMock.runAsync(any(UpdateTodoUseCaseRequest.class))).thenReturn(CompletableFuture.completedFuture(null));
        when(updateTodoUseCaseMock.hasErrorNotification()).thenReturn(true);

        // Act
        ResponseEntity<?> response = todoController.put(todoId, updateRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(updateTodoUseCaseMock, times(1)).runAsync(any(UpdateTodoUseCaseRequest.class));
        verify(updateTodoUseCaseMock, times(1)).hasErrorNotification();
        verify(notificationContextMock, times(1)).addErrorNotifications(updateTodoUseCaseMock);

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method put.");
    }

    // ==================== patch(id) Tests ====================

    @Test
    @DisplayName("patch - should set todo as done successfully")
    public void testPatch_Success() {
        // Arrange
        long todoId = 1L;
        var setDoneRequest = new SetDoneTodoRequest();
        setDoneRequest.setDone(true);

        when(setDoneTodoUseCaseMock.runAsync(any(SetDoneTodoUseCaseRequest.class))).thenReturn(CompletableFuture.completedFuture(null));
        when(setDoneTodoUseCaseMock.hasErrorNotification()).thenReturn(false);

        // Act
        ResponseEntity<?> response = todoController.patch(todoId, setDoneRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(SetDoneTodoResponse.class, response.getBody());

        verify(setDoneTodoUseCaseMock, times(1)).runAsync(any(SetDoneTodoUseCaseRequest.class));
        verify(setDoneTodoUseCaseMock, times(1)).hasErrorNotification();

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method patch.");
    }

    @Test
    @DisplayName("patch - should return bad request when todo not found")
    public void testPatch_NotFound() {
        // Arrange
        long todoId = 999L;
        var setDoneRequest = new SetDoneTodoRequest();
        setDoneRequest.setDone(true);

        when(setDoneTodoUseCaseMock.runAsync(any(SetDoneTodoUseCaseRequest.class))).thenReturn(CompletableFuture.completedFuture(null));
        when(setDoneTodoUseCaseMock.hasErrorNotification()).thenReturn(true);

        // Act
        ResponseEntity<?> response = todoController.patch(todoId, setDoneRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(setDoneTodoUseCaseMock, times(1)).runAsync(any(SetDoneTodoUseCaseRequest.class));
        verify(setDoneTodoUseCaseMock, times(1)).hasErrorNotification();
        verify(notificationContextMock, times(1)).addErrorNotifications(setDoneTodoUseCaseMock);

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method patch.");
    }

    @Test
    @DisplayName("patch - should set todo as not done successfully")
    public void testPatch_SetNotDone() {
        // Arrange
        long todoId = 1L;
        var setDoneRequest = new SetDoneTodoRequest();
        setDoneRequest.setDone(false);

        when(setDoneTodoUseCaseMock.runAsync(any(SetDoneTodoUseCaseRequest.class))).thenReturn(CompletableFuture.completedFuture(null));
        when(setDoneTodoUseCaseMock.hasErrorNotification()).thenReturn(false);

        // Act
        ResponseEntity<?> response = todoController.patch(todoId, setDoneRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(setDoneTodoUseCaseMock, times(1)).runAsync(any(SetDoneTodoUseCaseRequest.class));

        assertThat(logCaptor.getInfoLogs())
                .contains("Start controller TodoController > method patch.");
    }
}