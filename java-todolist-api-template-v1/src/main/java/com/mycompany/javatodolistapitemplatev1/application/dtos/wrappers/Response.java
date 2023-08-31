package com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mycompany.javatodolistapitemplatev1.shared.notification.models.NotificationMessage;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

import io.micrometer.common.lang.Nullable;

public class Response {
    public boolean succeeded;

    public String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<NotificationMessage> errors;

    public Response(boolean succeeded, List<NotificationMessage> errors) {
        this(succeeded, null, errors);
    }

    public Response(boolean succeeded, @Nullable String message, @Nullable List<NotificationMessage> errors) {
        this.succeeded = succeeded;
        this.errors = errors;

        if (message == null || message.trim().isEmpty())
            this.message = succeeded ? MsgUltil.RESPONSE_SUCCEEDED_MESSAGE()[1]
                    : MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST()[1];
        else
            this.message = message;
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

    public List<NotificationMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<NotificationMessage> errors) {
        this.errors = errors;
    }
}