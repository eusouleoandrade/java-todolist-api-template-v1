package com.mycompany.javatodolistapitemplatev1.presentation.controllers.v1;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoRequest;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.GetPaginatedTodoListsUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.CreateTodoResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListPagedResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.NotificationMessagesResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.ICreateTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetPaginatedTodoListsUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoListUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.CreateTodoRequestMapper;
import com.mycompany.javatodolistapitemplatev1.application.mappers.GetTodoListUseCaseResponseMapper;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoUseCaseResponseMapper;
import com.mycompany.javatodolistapitemplatev1.shared.notification.contexts.NotificationContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/todo")
@Tag(name = "Todo", description = "Endpoints related to todo list")
public class TodoController {

    private final IGetTodoListUseCase getTodoListUseCase;
    private final IGetPaginatedTodoListsUseCase getPaginatedTodoListsUseCase;
    private final IGetTodoUseCase getTodoUseCase;
    private final GetTodoListUseCaseResponseMapper getTodoListUseCaseResponseMapper;
    private final TodoUseCaseResponseMapper todoUseCaseResponseMapper;
    private final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final NotificationContext notificationContext;
    private final ICreateTodoUseCase createTodoUseCase;
    private final CreateTodoRequestMapper createTodoRequestMapper;

    @Value("${paginationSettings.maxPageSize}")
    private int maxPageSize;

    @Value("${paginationSettings.defaultPageSize}")
    private int defaultPageSize;

    @Value("${paginationSettings.initialPagination}")
    private int initialPagination;

    public TodoController(IGetTodoListUseCase getTodoListUseCase,
            GetTodoListUseCaseResponseMapper getTodoListUseCaseResponseMapper,
            IGetPaginatedTodoListsUseCase getPaginatedTodoListsUseCase,
            TodoUseCaseResponseMapper todoUseCaseResponseMapper,
            IGetTodoUseCase getTodoUseCase,
            NotificationContext notificationContext,
            ICreateTodoUseCase createTodoUseCase,
            CreateTodoRequestMapper createTodoRequestMapper) {

        this.getTodoListUseCase = getTodoListUseCase;
        this.getTodoListUseCaseResponseMapper = getTodoListUseCaseResponseMapper;
        this.todoUseCaseResponseMapper = todoUseCaseResponseMapper;
        this.getPaginatedTodoListsUseCase = getPaginatedTodoListsUseCase;
        this.getTodoUseCase = getTodoUseCase;
        this.notificationContext = notificationContext;
        this.createTodoUseCase = createTodoUseCase;
        this.createTodoRequestMapper = createTodoRequestMapper;
    }

    @GetMapping(value = "/")
    @Operation(summary = "Get todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Processed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetTodoListResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) })
    })
    public ResponseEntity<GetTodoListResponse> getAll() {

        logger.info(String.format("Start controller %s > method getAll.",
                TodoController.class.getSimpleName()));

        var useCaseResponse = getTodoListUseCase.runAsync().join();

        var todoQueryList = useCaseResponse.stream()
                .map(getTodoListUseCaseResponseMapper::convertTodoQuery)
                .collect(Collectors.toList());

        logger.info(String.format("Finishes successfully controller %s > method getAll.",
                TodoController.class.getSimpleName()));

        return ResponseEntity.ok(new GetTodoListResponse(todoQueryList));
    }

    @GetMapping(value = "")
    @Operation(summary = "Get todos paginated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Processed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetTodoListPagedResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) })
    })
    public ResponseEntity<GetTodoListPagedResponse> getPaginated(
            @RequestParam(name = "page_number") int pageNumber,
            @RequestParam(name = "page_size") int pageSize) {

        logger.info(String.format("Start controller %s > method getPaginated.",
                TodoController.class.getSimpleName()));

        var useCaseRequest = new GetPaginatedTodoListsUseCaseRequest(pageNumber,
                pageSize, maxPageSize, defaultPageSize, initialPagination);

        var useCaseResponse = getPaginatedTodoListsUseCase.runAsync(useCaseRequest).join();

        var todoQueryList = useCaseResponse.todoListUseCaseResponse.stream()
                .map(todoUseCaseResponseMapper::convertTodoQuery).collect(Collectors.toList());

        var response = new GetTodoListPagedResponse(todoQueryList, useCaseResponse.getPageNumber(),
                useCaseResponse.getPageSize(), useCaseResponse.getTotalPages(),
                useCaseResponse.getTotalRecords());

        logger.info(String.format("Finishes successfully controller %s > method getPaginated.",
                TodoController.class.getSimpleName()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Processed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GetTodoResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) })
    })
    public ResponseEntity<?> get(@PathVariable long id) {

        logger.info(String.format("Start controller %s > method get.",
                TodoController.class.getSimpleName()));

        var useCaseResponse = getTodoUseCase.runAsync(id).join();

        if (getTodoUseCase.hasErrorNotification()) {
            notificationContext.addErrorNotifications(getTodoUseCase);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var response = todoUseCaseResponseMapper.convertTodoQuery(useCaseResponse);

        logger.info(String.format("Finishes successfully controller %s > method get.",
                TodoController.class.getSimpleName()));

        return ResponseEntity.ok(new GetTodoResponse(response));
    }

    @PostMapping
    @Operation(summary = "Post todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CreateTodoResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) })
    })
    public ResponseEntity<?> post(@RequestBody CreateTodoRequest request) {

        logger.info(String.format("Start controller %s > method post.",
                TodoController.class.getSimpleName()));

        var useCaseRequest = createTodoRequestMapper.convertCreateTodoUseCaseRequest(request);

        var usecaseResponse = createTodoUseCase.runAsync(useCaseRequest).join();

        if (createTodoUseCase.hasErrorNotification()) {

            notificationContext.addErrorNotifications(createTodoUseCase);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var response = todoUseCaseResponseMapper.convertTodoQuery(usecaseResponse);

        logger.info(String.format("Finishes successfully controller %s > method post.",
                TodoController.class.getSimpleName()));

        return new ResponseEntity<>(new CreateTodoResponse(response), HttpStatus.CREATED);
    }

    // TODO: Return create Delete Http Method
}