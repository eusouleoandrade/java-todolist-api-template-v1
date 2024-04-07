package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

public class UpdateTodoRequest {

    private String title;

    private Boolean done;

    public UpdateTodoRequest(String title, Boolean done) {
        this.title = title;
        this.done = done;
    }

    public UpdateTodoRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}