package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import com.mycompany.javatodolistapitemplatev1.shared.notification.interfaces.INotifiable;

public interface IDeleteTodoUseCase extends INotifiable, IUseCaseWithRequest<Long, Boolean> {
}
