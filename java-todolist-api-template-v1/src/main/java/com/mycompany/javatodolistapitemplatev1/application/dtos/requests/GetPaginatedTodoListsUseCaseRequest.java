package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import lombok.Getter;

public class GetPaginatedTodoListsUseCaseRequest {

    @Getter
    private int pageNumber;

    @Getter
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
}