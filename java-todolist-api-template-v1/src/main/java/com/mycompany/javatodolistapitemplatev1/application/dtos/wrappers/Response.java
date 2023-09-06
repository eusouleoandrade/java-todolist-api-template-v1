package com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers;

import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

public abstract class Response {
    public boolean succeeded;

    public String message;

    public Response(boolean succeeded, String message) {
        this.succeeded = succeeded;
        this.message = message;
    }

    public Response(boolean succeeded) {

        this.succeeded = succeeded;

        this.message = succeeded ? MsgUltil.RESPONSE_SUCCEEDED_MESSAGE()[1]
                : MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST()[1];
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
