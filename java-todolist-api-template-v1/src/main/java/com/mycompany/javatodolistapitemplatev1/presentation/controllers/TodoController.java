package com.mycompany.javatodolistapitemplatev1.presentation.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.GetPaginatedTodoListsUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetPaginatedTodoListsUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.PagedResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetPaginatedTodoListsUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoListUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.GetTodoListUseCaseResponseMapper;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoUseCaseResponseMapper;

@RestController
@RequestMapping("/todo")
public class TodoController {

        private final IGetTodoListUseCase getTodoListUseCase;

        private final IGetPaginatedTodoListsUseCase getPaginatedTodoListsUseCase;

        private final GetTodoListUseCaseResponseMapper getTodoListUseCaseResponseMapper;

        private final TodoUseCaseResponseMapper todoUseCaseResponseMapperl;

        private final Logger logger = LoggerFactory.getLogger(TodoController.class);

        private final Environment environment;

        public TodoController(IGetTodoListUseCase getTodoUseCase,
                        GetTodoListUseCaseResponseMapper getTodoListUseCaseResponseMapper,
                        IGetPaginatedTodoListsUseCase getPaginatedTodoListsUseCase,
                        TodoUseCaseResponseMapper todoUseCaseResponseMapper,
                        Environment environment) {

                this.getTodoListUseCase = getTodoUseCase;
                this.getTodoListUseCaseResponseMapper = getTodoListUseCaseResponseMapper;
                this.todoUseCaseResponseMapperl = todoUseCaseResponseMapper;
                this.getPaginatedTodoListsUseCase = getPaginatedTodoListsUseCase;
                this.environment = environment;
        }

        // @GetMapping
        // public ResponseEntity<List<TodoQuery>> getAll() {

        // logger.info(String.format("Start controller %s > method getAll.",
        // TodoController.class.getSimpleName()));

        // var useCaseResponse = getTodoListUseCase.runAsync().join();

        // var todoQueryList = useCaseResponse.stream()
        // .map(getTodoListUseCaseResponseMapper::convertTodoQuery)
        // .collect(Collectors.toList());

        // logger.info(String.format("Finishes successfully controller %s > method
        // getAll.",
        // TodoController.class.getSimpleName()));

        // return new ResponseEntity<List<TodoQuery>>(todoQueryList, HttpStatus.OK);
        // }

        @GetMapping
        public ResponseEntity<PagedResponse<List<TodoQuery>>> getPaginated(
                        @RequestParam(name = "page_number") int pageNumber,
                        @RequestParam(name = "page_size") int pageSize) {

                logger.info(String.format("Start controller %s > method getPaginated.",
                                TodoController.class.getSimpleName()));

                var useCaseRequest = new GetPaginatedTodoListsUseCaseRequest(
                                pageNumber,
                                pageSize,
                                environment.getProperty("PaginationSettings:MaxPageSize", Integer.class),
                                environment.getProperty("PaginationSettings:DefaultPageSize", Integer.class),
                                environment.getProperty("PaginationSettings:InitialPagination", Integer.class));

                var useCaseResponse = getPaginatedTodoListsUseCase.runAsync(useCaseRequest).join();

                var todoQueryList = useCaseResponse.todoListUseCaseResponse.stream()
                                .map(todoUseCaseResponseMapperl::convertTodoQuery)
                                .collect(Collectors.toList());

                var response = new PagedResponse<List<TodoQuery>>(todoQueryList, useCaseResponse.getPageNumber(),
                                useCaseResponse.getPageSize(), useCaseResponse.getTotalPages(),
                                useCaseResponse.getTotalRecords());

                logger.info(String.format("Finishes successfully controller %s > method getPaginated.",
                                TodoController.class.getSimpleName()));

                return ResponseEntity.ok(response);
        }
}