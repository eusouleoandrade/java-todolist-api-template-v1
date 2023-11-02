package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import lombok.Getter;

public class SetDoneTodoUseCaseRequest {

    @Getter
    public long id;

    @Getter
    public Boolean done;

    public SetDoneTodoUseCaseRequest(long id, Boolean done) {
        this.id = id;
        this.done = done;
    }
}