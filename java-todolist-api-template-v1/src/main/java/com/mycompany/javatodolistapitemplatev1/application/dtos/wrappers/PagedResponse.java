package com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers;

public abstract class PagedResponse extends Response {

    public int pageNumber;

    public int pageSize;

    public int totalPages;

    public int totalRecords;

    public PagedResponse(int pageNumber, int pageSize, int totalPages, int totalRecords) {
        super(true);

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
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
}