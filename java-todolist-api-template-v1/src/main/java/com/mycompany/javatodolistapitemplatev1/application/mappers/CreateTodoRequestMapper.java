package com.mycompany.javatodolistapitemplatev1.application.mappers;

import org.springframework.stereotype.Component;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoUseCaseRequest;

@Component
public class CreateTodoRequestMapper {

    public CreateTodoUseCaseRequest convertCreateTodoUseCaseRequest(CreateTodoRequest request) {

        return new CreateTodoUseCaseRequest(request.getTitle());
    }
}