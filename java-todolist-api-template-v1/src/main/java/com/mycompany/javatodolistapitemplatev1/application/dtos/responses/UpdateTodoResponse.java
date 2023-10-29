package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.Response;

public class UpdateTodoResponse extends Response {

    public UpdateTodoResponse(boolean succeeded) {
        super(succeeded);
    }
}