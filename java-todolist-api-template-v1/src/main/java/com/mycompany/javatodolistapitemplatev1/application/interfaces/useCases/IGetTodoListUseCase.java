package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import java.util.List;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;

public interface IGetTodoListUseCase extends IUseCaseWithoutRequest<List<GetTodoListUseCaseResponse>> {
}