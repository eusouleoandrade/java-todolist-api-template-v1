package com.mycompany.javatodolistapitemplatev1.application.usecases;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.ICreateTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.CreateTodoUseCaseRequestMapper;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;
import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.Notifiable;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

@Service
@RequestScope
public class CreateTodoUseCase extends Notifiable implements ICreateTodoUseCase {

    private final Logger logger = LoggerFactory.getLogger(CreateTodoUseCase.class);

    private final ITodoRepositoryAsync todoRepositoryAsync;

    private final CreateTodoUseCaseRequestMapper createTodoUseCaseRequestMapper;

    private final TodoMapper todoMapper;

    public CreateTodoUseCase(ITodoRepositoryAsync todoRepositoryAsync,
            CreateTodoUseCaseRequestMapper createTodoUseCaseRequestMapper, TodoMapper todoMapper) {

        this.todoRepositoryAsync = todoRepositoryAsync;
        this.createTodoUseCaseRequestMapper = createTodoUseCaseRequestMapper;
        this.todoMapper = todoMapper;
    }

    @Override
    public CompletableFuture<TodoUseCaseResponse> runAsync(CreateTodoUseCaseRequest request) {

        logger.info("Start useCase %s > method runAsync.".formatted(
                CreateTodoUseCase.class.getSimpleName()));

        if (request.hasErrorNotification()) {

            addErrorNotifications(request);
            return CompletableFuture.completedFuture(null);
        }

        var todo = createTodoUseCaseRequestMapper.convertTodo(request);

        var todoRepositoryResponse = todoRepositoryAsync.createAsync(todo).join();

        if (todoRepositoryResponse == null) {

            addErrorNotification(MsgUltil.OBJECT_X0_IS_NULL(null)[0],
                    MsgUltil.OBJECT_X0_IS_NULL("Todo repository response")[1]);

            return CompletableFuture.completedFuture(null);
        }

        logger.info("Finishes successfully useCase %s > method runAsync.".formatted(
                CreateTodoUseCase.class.getSimpleName()));

        return CompletableFuture.completedFuture(todoMapper.convertTodoUseCaseResponse(todoRepositoryResponse));
    }
}