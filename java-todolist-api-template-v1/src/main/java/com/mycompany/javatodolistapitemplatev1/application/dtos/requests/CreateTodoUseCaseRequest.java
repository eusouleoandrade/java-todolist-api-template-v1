package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.Notifiable;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

public class CreateTodoUseCaseRequest extends Notifiable {

    public String title;

    public boolean done = false;

    public CreateTodoUseCaseRequest(String title) {

        this.title = title;

        Validade();
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    private void Validade() {
        if (this.title == null || title.trim().isEmpty())
            addErrorNotification(MsgUltil.X0_IS_REQUIRED(null)[0], MsgUltil.X0_IS_REQUIRED(this.title)[1]);
    }
}