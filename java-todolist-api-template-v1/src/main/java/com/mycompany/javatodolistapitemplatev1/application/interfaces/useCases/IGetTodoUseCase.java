package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.shared.notification.interfaces.INotifiable;

public interface IGetTodoUseCase extends INotifiable, IUseCaseWithRequest<Long, TodoUseCaseResponse> {
}
