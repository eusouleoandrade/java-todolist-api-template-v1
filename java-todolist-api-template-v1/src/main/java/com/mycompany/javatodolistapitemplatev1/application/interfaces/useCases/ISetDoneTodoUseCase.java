package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.SetDoneTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.shared.notification.interfaces.INotifiable;

public interface ISetDoneTodoUseCase extends INotifiable, IUseCaseWithRequest<SetDoneTodoUseCaseRequest, Boolean> {
}