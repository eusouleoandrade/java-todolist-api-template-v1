package com.mycompany.javatodolistapitemplatev1.application.usecases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;
import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.Notifiable;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.concurrent.CompletableFuture;

@Service
@RequestScope
public class GetTodoUseCase extends Notifiable implements IGetTodoUseCase {

    private final ITodoRepositoryAsync todoRepositoryAsync;

    private final TodoMapper todoMapper;

    private final Logger logger = LoggerFactory.getLogger(GetTodoUseCase.class);

    public GetTodoUseCase(ITodoRepositoryAsync todoRepositoryAsync, TodoMapper todoMapper) {
        this.todoRepositoryAsync = todoRepositoryAsync;
        this.todoMapper = todoMapper;
    }

    @Override
    public CompletableFuture<TodoUseCaseResponse> runAsync(Long id) {

        logger.info("Start useCase %s > method runAsync.".formatted(GetTodoUseCase.class.getSimpleName()));

        Validate(id);

        if (hasErrorNotification())
            return CompletableFuture.completedFuture(null);

        var entity = todoRepositoryAsync.getAsync(id).join();

        if (entity == null) {
            addErrorNotification(MsgUltil.DATA_OF_X0_X1_NOT_FOUND(null, null)[0],
                    MsgUltil.DATA_OF_X0_X1_NOT_FOUND("Todo", Long.toString(id))[1]);

            return CompletableFuture.completedFuture(null);
        }

        var useCaseResponse = todoMapper.convertTodoUseCaseResponse(entity);

        logger.info(
                "Finishes successfully useCase %s > method runAsync.".formatted(GetTodoUseCase.class.getSimpleName()));

        return CompletableFuture.completedFuture(useCaseResponse);
    }

    private void Validate(long id) {

        if (id <= 0)
            addErrorNotification(MsgUltil.IDENTIFIER_X0_IS_INVALID(null)[0],
                    MsgUltil.IDENTIFIER_X0_IS_INVALID(Long.toString(id))[1]);
    }
}