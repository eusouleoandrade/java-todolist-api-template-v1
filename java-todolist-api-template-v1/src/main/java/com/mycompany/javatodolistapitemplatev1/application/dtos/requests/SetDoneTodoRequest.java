package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

public class SetDoneTodoRequest {

    private Boolean done;

    public SetDoneTodoRequest(Boolean done) {
        this.done = done;
    }

    public SetDoneTodoRequest() {
    }

    public Boolean getDone() {
        return this.done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}