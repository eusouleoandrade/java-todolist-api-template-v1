package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.Notifiable;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

import lombok.Getter;

public class CreateTodoUseCaseRequest extends Notifiable {

    @Getter
    public String title;

    @Getter
    public boolean done = false;

    public CreateTodoUseCaseRequest(String title) {

        this.title = title;

        Validade();
    }

    private void Validade() {
        if (this.title == null || title.trim().isEmpty())
            addErrorNotification(MsgUltil.X0_IS_REQUIRED(null)[0], MsgUltil.X0_IS_REQUIRED(this.title)[1]);
    }
}