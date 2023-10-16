package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.Response;

import lombok.Getter;
import lombok.Setter;

public class CreateTodoResponse extends Response {

    @JsonProperty("data")
    @Getter
    @Setter
    public TodoQuery todo;

    public CreateTodoResponse(TodoQuery todo) {
        super(true);

        this.todo = todo;
    }
}