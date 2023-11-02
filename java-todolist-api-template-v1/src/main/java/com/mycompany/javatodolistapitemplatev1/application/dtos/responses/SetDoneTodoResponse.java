package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.Response;

public class SetDoneTodoResponse extends Response {

    public SetDoneTodoResponse(boolean succeeded) {
        super(succeeded);
    }
}