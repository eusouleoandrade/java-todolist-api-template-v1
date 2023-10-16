package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import java.util.List;

import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.Response;
import com.mycompany.javatodolistapitemplatev1.shared.notification.models.NotificationMessage;

import lombok.Getter;
import lombok.Setter;

public class NotificationMessagesResponse extends Response {

    @Getter
    @Setter
    public List<NotificationMessage> errors;

    public NotificationMessagesResponse(List<NotificationMessage> errors) {
        super(false);

        this.errors = errors;
    }
}