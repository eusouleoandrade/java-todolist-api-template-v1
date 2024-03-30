package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

public class GetPaginatedTodoListsUseCaseRequest {

    private int pageNumber;

    private int pageSize;

    public GetPaginatedTodoListsUseCaseRequest(
            int pageNumber,
            int pageSize,
            int maxPageSize,
            int defaultPageSize,
            int initialPagination) {

        this.pageNumber = pageNumber <= 0 ? initialPagination : pageNumber;

        this.pageSize = Math.min(pageSize, maxPageSize);
        this.pageSize = this.pageSize <= 0 ? defaultPageSize : this.pageSize;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }
}