package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

public class GetTodoListUseCaseResponse {

    public long id;

    public String title;

    public boolean done;

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }
}