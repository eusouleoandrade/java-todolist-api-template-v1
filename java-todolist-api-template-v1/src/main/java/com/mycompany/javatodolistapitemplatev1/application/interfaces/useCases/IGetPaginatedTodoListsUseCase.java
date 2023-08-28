package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.GetPaginatedTodoListsUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetPaginatedTodoListsUseCaseResponse;

public interface IGetPaginatedTodoListsUseCase
        extends IUseCaseWithRequest<GetPaginatedTodoListsUseCaseRequest, GetPaginatedTodoListsUseCaseResponse> {

}
