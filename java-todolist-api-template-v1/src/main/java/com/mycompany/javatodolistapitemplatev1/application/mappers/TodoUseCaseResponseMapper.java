package com.mycompany.javatodolistapitemplatev1.application.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.TodoUseCaseResponse;

@Component
public class TodoUseCaseResponseMapper {

    private final ModelMapper modelMapper;

    public TodoUseCaseResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TodoQuery convertTodoQuery(TodoUseCaseResponse todoUseCaseResponse) {
        return modelMapper.map(todoUseCaseResponse, TodoQuery.class);
    }
}