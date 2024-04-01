package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

public class UpdateTodoRequest {

    public String title;

    public Boolean done;

    public UpdateTodoRequest(String title, Boolean done) {
        this.title = title;
        this.done = done;
    }

    public UpdateTodoRequest() {
    }
}