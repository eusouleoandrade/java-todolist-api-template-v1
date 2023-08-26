package com.mycompany.javatodolistapitemplatev1.application.usecases;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoListUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;

@Service
public class GetTodoListUseCase implements IGetTodoListUseCase {

    private final TodoMapper todoMapper;

    private final ITodoRepositoryAsync todoRepositoryAsync;

    private final Logger logger = LoggerFactory.getLogger(GetTodoListUseCase.class);

    public GetTodoListUseCase(TodoMapper todoMapper, ITodoRepositoryAsync todoRepositoryAsync) {
        this.todoMapper = todoMapper;
        this.todoRepositoryAsync = todoRepositoryAsync;
    }

    @Override
    public CompletableFuture<List<GetTodoListUseCaseResponse>> runAsync() {

        // Loger start
        logger.info(String.format("Start useCase %s > method runAsync.",
                GetTodoListUseCase.class.getSimpleName()));

        // Get todo list of repository
        var entities = todoRepositoryAsync.getTodoListAsync().join();

        // Convert Todo list to GetTodoListUseCaseResponse list
        var useCaseResponse = entities.stream()
                .map(todoMapper::convertGetTodoListUseCaseResponse)
                .collect(Collectors.toList());

        // Loger finishes successfully
        logger.info(String.format("Finishes successfully useCase %s > method runAsync.",
                GetTodoListUseCase.class.getSimpleName()));

        // Return
        return CompletableFuture.completedFuture(useCaseResponse);
    }
}