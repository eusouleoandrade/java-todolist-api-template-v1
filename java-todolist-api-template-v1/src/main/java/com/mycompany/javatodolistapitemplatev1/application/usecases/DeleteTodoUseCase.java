package com.mycompany.javatodolistapitemplatev1.application.usecases;

import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IDeleteTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.Notifiable;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.concurrent.CompletableFuture;

@Service
@RequestScope
public class DeleteTodoUseCase extends Notifiable implements IDeleteTodoUseCase {

    private final Logger logger = LoggerFactory.getLogger(DeleteTodoUseCase.class);

    private final ITodoRepositoryAsync todoRepositoryAsync;

    private final IGetTodoUseCase getTodoUseCase;

    public DeleteTodoUseCase(ITodoRepositoryAsync todoRepositoryAsync, IGetTodoUseCase getTodoUseCase) {

        this.todoRepositoryAsync = todoRepositoryAsync;
        this.getTodoUseCase = getTodoUseCase;
    }

    @Override
    public CompletableFuture<Boolean> runAsync(Long id) {

        logger.info("Start useCase %s > method runAsync.".formatted(DeleteTodoUseCase.class.getSimpleName()));

        var getTodoUseCaseResponse = getTodoUseCase.runAsync(id).join();

        if (getTodoUseCase.hasErrorNotification()) {

            addErrorNotifications(getTodoUseCase);
            return CompletableFuture.completedFuture(false);
        }

        var deleted = todoRepositoryAsync.deleteAsync(getTodoUseCaseResponse.getId()).join();

        if (!deleted)
            addErrorNotification(MsgUltil.FAILED_TO_REMOVE_X0(null)[0], MsgUltil.FAILED_TO_REMOVE_X0("Todo")[1]);
        else
            logger.info("Finishes successfully useCase %s > method runAsync.".formatted(
                    DeleteTodoUseCase.class.getSimpleName()));

        return CompletableFuture.completedFuture(deleted);
    }
}