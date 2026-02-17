package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mycompany.javatodolistapitemplatev1.application.dtos.queries.TodoQuery;
import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.PagedResponse;

import java.util.List;

public class GetTodoListPagedResponse extends PagedResponse {

    @JsonProperty("data")
    public List<TodoQuery> todos;

    public GetTodoListPagedResponse(List<TodoQuery> todos,
            int pageNumber,
            int pageSize,
            int totalPages,
            int totalRecords) {

        super(pageNumber, pageSize, totalPages, totalRecords);

        this.todos = todos;
    }

    public List<TodoQuery> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoQuery> todos) {
        this.todos = todos;
    }
}