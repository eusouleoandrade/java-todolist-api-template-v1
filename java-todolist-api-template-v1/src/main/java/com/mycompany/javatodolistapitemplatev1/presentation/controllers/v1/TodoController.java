package com.mycompany.javatodolistapitemplatev1.presentation.controllers.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.GetPaginatedTodoListsUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.PagedResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.ResponseWithData;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetPaginatedTodoListsUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoListUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.GetTodoListUseCaseResponseMapper;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoUseCaseResponseMapper;
import com.mycompany.javatodolistapitemplatev1.shared.notification.contexts.NotificationContext;

@RestController
@RequestMapping("api/v1/todo")
public class TodoController {

    private final IGetTodoListUseCase getTodoListUseCase;
    private final IGetPaginatedTodoListsUseCase getPaginatedTodoListsUseCase;
    private final IGetTodoUseCase getTodoUseCase;
    private final GetTodoListUseCaseResponseMapper getTodoListUseCaseResponseMapper;
    private final TodoUseCaseResponseMapper todoUseCaseResponseMapper;
    private final Logger logger = LoggerFactory.getLogger(TodoController.class);
    private final NotificationContext notificationContext;

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
            NotificationContext notificationContext) {

        this.getTodoListUseCase = getTodoListUseCase;
        this.getTodoListUseCaseResponseMapper = getTodoListUseCaseResponseMapper;
        this.todoUseCaseResponseMapper = todoUseCaseResponseMapper;
        this.getPaginatedTodoListsUseCase = getPaginatedTodoListsUseCase;
        this.getTodoUseCase = getTodoUseCase;
        this.notificationContext = notificationContext;
    }

    @GetMapping
    public ResponseEntity<ResponseWithData<List<TodoQuery>>> getAll() {

        logger.info(String.format("Start controller %s > method getAll.",
                TodoController.class.getSimpleName()));

        var useCaseResponse = getTodoListUseCase.runAsync().join();

        var todoQueryList = useCaseResponse.stream()
                .map(getTodoListUseCaseResponseMapper::convertTodoQuery)
                .collect(Collectors.toList());

        logger.info(String.format("Finishes successfully controller %s > method getAll.",
                TodoController.class.getSimpleName()));

        return ResponseEntity.ok(new ResponseWithData<List<TodoQuery>>(todoQueryList, true, null));
    }

    @GetMapping("paginated")
    public ResponseEntity<PagedResponse<List<TodoQuery>>> getPaginated(
            @RequestParam(name = "page_number") int pageNumber,
            @RequestParam(name = "page_size") int pageSize) {

        logger.info(String.format("Start controller %s > method getPaginated.",
                TodoController.class.getSimpleName()));

        var useCaseRequest = new GetPaginatedTodoListsUseCaseRequest(pageNumber,
                pageSize, maxPageSize, defaultPageSize, initialPagination);

        var useCaseResponse = getPaginatedTodoListsUseCase.runAsync(useCaseRequest).join();

        var todoQueryList = useCaseResponse.todoListUseCaseResponse.stream()
                .map(todoUseCaseResponseMapper::convertTodoQuery).collect(Collectors.toList());

        var response = new PagedResponse<List<TodoQuery>>(todoQueryList, useCaseResponse.getPageNumber(),
                useCaseResponse.getPageSize(), useCaseResponse.getTotalPages(),
                useCaseResponse.getTotalRecords());

        logger.info(String.format("Finishes successfully controller %s > method getPaginated.",
                TodoController.class.getSimpleName()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
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

        return ResponseEntity.ok(new ResponseWithData<TodoQuery>(response, true));
    }
}