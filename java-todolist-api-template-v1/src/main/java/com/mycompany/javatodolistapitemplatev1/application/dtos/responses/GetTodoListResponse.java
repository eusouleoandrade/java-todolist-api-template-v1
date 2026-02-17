package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.Response;

import java.util.List;

public class GetTodoListResponse extends Response {

    @JsonProperty("data")
    public List<TodoQuery> todos;

    public GetTodoListResponse(List<TodoQuery> todos) {
        super(true);

        this.todos = todos;
    }

    public List<TodoQuery> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoQuery> todos) {
        this.todos = todos;
    }
}
