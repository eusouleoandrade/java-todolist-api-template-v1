package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.Response;
import com.mycompany.javatodolistapitemplatev1.shared.notification.models.NotificationMessage;

import java.util.List;

public class NotificationMessagesResponse extends Response {

    public List<NotificationMessage> errors;

    public NotificationMessagesResponse(List<NotificationMessage> errors) {
        super(false);

        this.errors = errors;
    }

    public List<NotificationMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<NotificationMessage> errors) {
        this.errors = errors;
    }
}