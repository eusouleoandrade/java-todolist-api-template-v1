package com.mycompany.javatodolistapitemplatev1.application.usecases;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoListUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

@Service
public class GetTodoListUseCase implements IGetTodoListUseCase {

    private final TodoMapper todoMapper;
    private final ITodoRepositoryAsync todoRepositoryAsync;

    @Autowired
    public GetTodoListUseCase(TodoMapper todoMapper, ITodoRepositoryAsync todoRepositoryAsync) {
        this.todoMapper = todoMapper;
        this.todoRepositoryAsync = todoRepositoryAsync;
    }

    @Override
    public CompletableFuture<List<GetTodoListUseCaseResponse>> runAsync() {

        // Get todo list of repository
        List<Todo> entities = todoRepositoryAsync.getTodoListAsync().join();

        // Convert Todo list to GetTodoListUseCaseResponse list
        List<GetTodoListUseCaseResponse> useCaseResponse = entities.stream()
                .map(todoMapper::convertGetTodoListUseCaseResponse)
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(useCaseResponse);
    }
}