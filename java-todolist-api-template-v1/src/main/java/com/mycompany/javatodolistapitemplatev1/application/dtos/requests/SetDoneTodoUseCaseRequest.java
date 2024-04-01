package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

public class SetDoneTodoUseCaseRequest {

    private long id;

    private Boolean done;

    public SetDoneTodoUseCaseRequest(long id, Boolean done) {
        this.id = id;
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public Boolean getDone() {
        return done;
    }
}