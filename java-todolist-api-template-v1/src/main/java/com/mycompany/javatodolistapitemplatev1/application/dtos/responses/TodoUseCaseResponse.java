package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

public class TodoUseCaseResponse {

    public long id;

    public String title;

    public boolean done;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}