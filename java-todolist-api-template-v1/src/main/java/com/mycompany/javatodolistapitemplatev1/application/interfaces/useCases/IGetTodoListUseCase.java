package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;

import java.util.List;

public interface IGetTodoListUseCase extends IUseCaseWithoutRequest<List<GetTodoListUseCaseResponse>> {
}