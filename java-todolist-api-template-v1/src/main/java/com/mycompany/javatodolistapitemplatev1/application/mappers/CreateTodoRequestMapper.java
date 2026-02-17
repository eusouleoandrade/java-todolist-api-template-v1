package com.mycompany.javatodolistapitemplatev1.application.mappers;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoUseCaseRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateTodoRequestMapper {

    public CreateTodoUseCaseRequest convertCreateTodoUseCaseRequest(CreateTodoRequest request) {

        return new CreateTodoUseCaseRequest(request.getTitle());
    }
}