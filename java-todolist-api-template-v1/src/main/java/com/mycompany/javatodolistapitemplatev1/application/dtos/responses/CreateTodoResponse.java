package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.Response;

public class CreateTodoResponse extends Response {

    @JsonProperty("data")
    private TodoQuery todo;

    public CreateTodoResponse(TodoQuery todo) {
        super(true);

        this.todo = todo;
    }

    public TodoQuery getTodo() {
        return todo;
    }

    public void setTodo(TodoQuery todo) {
        this.todo = todo;
    }
}