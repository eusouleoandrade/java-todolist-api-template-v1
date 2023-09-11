package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.shared.notification.interfaces.INotifiable;

public interface ICreateTodoUseCase
                extends INotifiable, IUseCaseWithRequest<CreateTodoUseCaseRequest, TodoUseCaseResponse> {

}