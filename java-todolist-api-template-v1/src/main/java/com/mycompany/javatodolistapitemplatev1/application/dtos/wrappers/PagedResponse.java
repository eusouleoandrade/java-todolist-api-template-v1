package com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers;

public class PagedResponse<TData> extends ResponseWithData<TData> {

    public int pageNumber;

    public int pageSize;

    public int totalPages;

    public int totalRecords;

    public PagedResponse(TData data, int pageNumber, int pageSize, int totalPages, int totalRecords) {

        super(data, true, null);

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
    }
}