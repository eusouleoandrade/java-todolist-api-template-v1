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
}
