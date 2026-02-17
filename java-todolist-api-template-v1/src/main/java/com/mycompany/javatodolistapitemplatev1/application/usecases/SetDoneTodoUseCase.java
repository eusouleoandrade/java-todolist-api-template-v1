package com.mycompany.javatodolistapitemplatev1.application.usecases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.SetDoneTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.ISetDoneTodoUseCase;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;
import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.Notifiable;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.concurrent.CompletableFuture;

@Service
@RequestScope
public class SetDoneTodoUseCase extends Notifiable implements ISetDoneTodoUseCase {

    private final Logger logger = LoggerFactory.getLogger(SetDoneTodoUseCase.class);
    private final ITodoRepositoryAsync todoRepositoryAsync;
    private final IGetTodoUseCase getTodoUseCase;

    public SetDoneTodoUseCase(ITodoRepositoryAsync todoRepositoryAsync, IGetTodoUseCase getTodoUseCase) {

        this.todoRepositoryAsync = todoRepositoryAsync;
        this.getTodoUseCase = getTodoUseCase;
    }

    @Override
    public CompletableFuture<Boolean> runAsync(SetDoneTodoUseCaseRequest request) {

        logger.info("Start useCase %s > method runAsync.".formatted(
                SetDoneTodoUseCase.class.getSimpleName()));

        var todoUseCaseResponse = getTodoUseCase.runAsync(request.getId()).join();

        if (getTodoUseCase.hasErrorNotification()) {
            addErrorNotifications(getTodoUseCase);
            return CompletableFuture.completedFuture(false);
        }

        var todo = new Todo(todoUseCaseResponse.getId(), todoUseCaseResponse.getTitle(), request.getDone());

        var updated = todoRepositoryAsync.updateAsync(todo).join();

        if (!updated)
            addErrorNotification(MsgUltil.FAILED_TO_UPDATE_X0(null)[0], MsgUltil.FAILED_TO_UPDATE_X0("Todo")[1]);
        else
            logger.info("Finishes successfully useCase %s > method runAsync.".formatted(
                    SetDoneTodoUseCase.class.getSimpleName()));

        return CompletableFuture.completedFuture(updated);
    }
}