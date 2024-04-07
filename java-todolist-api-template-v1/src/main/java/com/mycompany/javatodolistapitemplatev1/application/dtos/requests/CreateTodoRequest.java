package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

public class CreateTodoRequest {

    private String title;

    public CreateTodoRequest(String title) {
        this.title = title;
    }

    public CreateTodoRequest() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}