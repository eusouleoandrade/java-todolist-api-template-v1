package com.mycompany.javatodolistapitemplatev1.presentation.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoListUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.GetTodoListUseCaseResponseMapper;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final IGetTodoListUseCase getTodoListUseCase;
    private final GetTodoListUseCaseResponseMapper getTodoListUseCaseResponseMapper;

    public TodoController(IGetTodoListUseCase getTodoUseCase,
            GetTodoListUseCaseResponseMapper getTodoListUseCaseResponseMapper) {

        this.getTodoListUseCase = getTodoUseCase;
        this.getTodoListUseCaseResponseMapper = getTodoListUseCaseResponseMapper;
    }

    @GetMapping
    public ResponseEntity<List<TodoQuery>> getAll() {

        // Join: Wait for the async task to complete (not blocking a thread)
        var useCaseResponse = getTodoListUseCase.runAsync().join();

        // Convert useCaseResponse list to todoQuery list
        var todoQueryList = useCaseResponse.stream()
                .map(getTodoListUseCaseResponseMapper::convertTodoQuery)
                .collect(Collectors.toList());

        // Response
        return new ResponseEntity<List<TodoQuery>>(todoQueryList, HttpStatus.OK);
    }
}