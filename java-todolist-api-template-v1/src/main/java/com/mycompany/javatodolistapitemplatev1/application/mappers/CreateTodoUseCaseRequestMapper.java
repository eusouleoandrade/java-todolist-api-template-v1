package com.mycompany.javatodolistapitemplatev1.application.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.CreateTodoUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

@Component
public class CreateTodoUseCaseRequestMapper {

    private final ModelMapper modelMapper;

    public CreateTodoUseCaseRequestMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Todo convertTodo(CreateTodoUseCaseRequest createTodoUseCaseRequest) {
        return modelMapper.map(createTodoUseCaseRequest, Todo.class);
    }
}
