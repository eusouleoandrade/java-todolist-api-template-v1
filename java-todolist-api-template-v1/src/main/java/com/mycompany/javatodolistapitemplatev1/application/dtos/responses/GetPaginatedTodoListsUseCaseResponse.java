package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import java.util.List;

public class GetPaginatedTodoListsUseCaseResponse {

    public int pageNumber;

    public int pageSize;

    public int totalPages;

    public int totalRecords;

    public List<TodoUseCaseResponse> todoListUseCaseResponse;

    public GetPaginatedTodoListsUseCaseResponse(int pageNumber, int pageSize, int totalPages, int totalRecords,
            List<TodoUseCaseResponse> todoListUseCaseResponse) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
        this.todoListUseCaseResponse = todoListUseCaseResponse;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<TodoUseCaseResponse> getTodoListUseCaseResponse() {
        return todoListUseCaseResponse;
    }

    public void setTodoListUseCaseResponse(List<TodoUseCaseResponse> todoListUseCaseResponse) {
        this.todoListUseCaseResponse = todoListUseCaseResponse;
    }
}
