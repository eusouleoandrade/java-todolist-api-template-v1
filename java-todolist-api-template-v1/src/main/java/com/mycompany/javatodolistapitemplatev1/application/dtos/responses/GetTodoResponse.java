package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.Response;

public class GetTodoResponse extends Response {

    @JsonProperty("data")
    public TodoQuery todo;

    public GetTodoResponse(TodoQuery todo) {
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