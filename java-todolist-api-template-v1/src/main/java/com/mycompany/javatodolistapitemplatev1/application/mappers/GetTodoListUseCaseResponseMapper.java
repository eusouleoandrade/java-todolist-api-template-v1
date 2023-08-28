package com.mycompany.javatodolistapitemplatev1.application.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetTodoListUseCaseResponse;

@Component
public class GetTodoListUseCaseResponseMapper {

    private final ModelMapper modelMapper;

    public GetTodoListUseCaseResponseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TodoQuery convertTodoQuery(GetTodoListUseCaseResponse getTodoListUseCaseResponse) {
        return modelMapper.map(getTodoListUseCaseResponse, TodoQuery.class);
    }
}
