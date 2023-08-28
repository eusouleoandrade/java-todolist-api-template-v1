package com.mycompany.javatodolistapitemplatev1.application.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

@Component
public class TodoMapper {
    private final ModelMapper modelMapper;

    public TodoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GetTodoListUseCaseResponse convertGetTodoListUseCaseResponse(Todo todo) {
        return modelMapper.map(todo, GetTodoListUseCaseResponse.class);
    }

    public TodoUseCaseResponse convertTodoUseCaseResponse(Todo todo) {
        return modelMapper.map(todo, TodoUseCaseResponse.class);
    }
}