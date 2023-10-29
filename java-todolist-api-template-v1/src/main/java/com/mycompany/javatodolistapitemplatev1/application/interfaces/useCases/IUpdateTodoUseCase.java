package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.UpdateTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.shared.notification.interfaces.INotifiable;

public interface IUpdateTodoUseCase extends INotifiable, IUseCaseWithRequest<UpdateTodoUseCaseRequest, Boolean> {
}
