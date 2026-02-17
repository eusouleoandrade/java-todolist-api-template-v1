package com.mycompany.javatodolistapitemplatev1.presentation.controllers.v1;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.*;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.*;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.*;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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
        private final IDeleteTodoUseCase deleteTodoUseCase;
        private final IUpdateTodoUseCase updateTodoUseCase;
        private final ISetDoneTodoUseCase setDoneTodoUseCase;

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
                        CreateTodoRequestMapper createTodoRequestMapper,
                        IDeleteTodoUseCase deleteTodoUseCase,
                        IUpdateTodoUseCase updateTodoUseCase,
                        ISetDoneTodoUseCase setDoneTodoUseCase) {

                this.getTodoListUseCase = getTodoListUseCase;
                this.getTodoListUseCaseResponseMapper = getTodoListUseCaseResponseMapper;
                this.todoUseCaseResponseMapper = todoUseCaseResponseMapper;
                this.getPaginatedTodoListsUseCase = getPaginatedTodoListsUseCase;
                this.getTodoUseCase = getTodoUseCase;
                this.notificationContext = notificationContext;
                this.createTodoUseCase = createTodoUseCase;
                this.createTodoRequestMapper = createTodoRequestMapper;
                this.deleteTodoUseCase = deleteTodoUseCase;
                this.updateTodoUseCase = updateTodoUseCase;
                this.setDoneTodoUseCase = setDoneTodoUseCase;
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

                logger.info("Start controller %s > method getAll.".formatted(
                                TodoController.class.getSimpleName()));

                var useCaseResponse = getTodoListUseCase.runAsync().join();

                var todoQueryList = useCaseResponse.stream()
                                .map(getTodoListUseCaseResponseMapper::convertTodoQuery)
                                .collect(Collectors.toList());

                logger.info("Finishes successfully controller %s > method getAll.".formatted(
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

                logger.info("Start controller %s > method getPaginated.".formatted(
                                TodoController.class.getSimpleName()));

                var useCaseRequest = new GetPaginatedTodoListsUseCaseRequest(pageNumber,
                                pageSize, maxPageSize, defaultPageSize, initialPagination);

                var useCaseResponse = getPaginatedTodoListsUseCase.runAsync(useCaseRequest).join();

                var todoQueryList = useCaseResponse.todoListUseCaseResponse.stream()
                                .map(todoUseCaseResponseMapper::convertTodoQuery).collect(Collectors.toList());

                var response = new GetTodoListPagedResponse(todoQueryList, useCaseResponse.getPageNumber(),
                                useCaseResponse.getPageSize(), useCaseResponse.getTotalPages(),
                                useCaseResponse.getTotalRecords());

                logger.info("Finishes successfully controller %s > method getPaginated.".formatted(
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

                logger.info("Start controller %s > method get.".formatted(
                                TodoController.class.getSimpleName()));

                var useCaseResponse = getTodoUseCase.runAsync(id).join();

                if (getTodoUseCase.hasErrorNotification()) {
                        notificationContext.addErrorNotifications(getTodoUseCase);
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                var response = todoUseCaseResponseMapper.convertTodoQuery(useCaseResponse);

                logger.info("Finishes successfully controller %s > method get.".formatted(
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

                logger.info("Start controller %s > method post.".formatted(
                                TodoController.class.getSimpleName()));

                var useCaseRequest = createTodoRequestMapper.convertCreateTodoUseCaseRequest(request);

                var usecaseResponse = createTodoUseCase.runAsync(useCaseRequest).join();

                if (createTodoUseCase.hasErrorNotification()) {

                        notificationContext.addErrorNotifications(createTodoUseCase);
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                var response = todoUseCaseResponseMapper.convertTodoQuery(usecaseResponse);

                logger.info("Finishes successfully controller %s > method post.".formatted(
                                TodoController.class.getSimpleName()));

                return new ResponseEntity<>(new CreateTodoResponse(response), HttpStatus.CREATED);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete todo")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Successfully Processed"),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) })
        })
        public ResponseEntity<?> delete(@PathVariable long id) {

                logger.info("Start controller %s > method delete.".formatted(
                                TodoController.class.getSimpleName()));

                deleteTodoUseCase.runAsync(id).join();

                if (deleteTodoUseCase.hasErrorNotification()) {

                        notificationContext.addErrorNotifications(deleteTodoUseCase);
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                logger.info("Finishes successfully controller %s > method delete.".formatted(
                                TodoController.class.getSimpleName()));

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update todo")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully Processed", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateTodoResponse.class)) }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) })
        })
        public ResponseEntity<?> put(@PathVariable long id, @RequestBody UpdateTodoRequest request) {

                logger.info("Start controller %s > method put.".formatted(
                                TodoController.class.getSimpleName()));

                updateTodoUseCase.runAsync(new UpdateTodoUseCaseRequest(id, request.getTitle(), request.getDone()))
                                .join();

                if (updateTodoUseCase.hasErrorNotification()) {

                        notificationContext.addErrorNotifications(updateTodoUseCase);
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                logger.info("Finishes successfully controller %s > method put.".formatted(
                                TodoController.class.getSimpleName()));

                return ResponseEntity.ok(new UpdateTodoResponse(true));
        }

        @PatchMapping("/{id}")
        @Operation(summary = "Set done todo")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully Processed", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = SetDoneTodoResponse.class)) }),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) }),
                        @ApiResponse(responseCode = "404", description = "Not Found", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationMessagesResponse.class)) })
        })
        public ResponseEntity<?> patch(@PathVariable long id, @RequestBody SetDoneTodoRequest request) {

                logger.info("Start controller %s > method patch.".formatted(
                                TodoController.class.getSimpleName()));

                setDoneTodoUseCase.runAsync(new SetDoneTodoUseCaseRequest(id, request.getDone())).join();

                if (setDoneTodoUseCase.hasErrorNotification()) {

                        notificationContext.addErrorNotifications(setDoneTodoUseCase);
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                return ResponseEntity.ok(new SetDoneTodoResponse(true));
        }
}