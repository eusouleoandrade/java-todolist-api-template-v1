package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.Notifiable;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

import lombok.Getter;

public class UpdateTodoUseCaseRequest extends Notifiable {

    @Getter
    public long id;

    @Getter
    public String title;

    @Getter
    public Boolean done;

    public UpdateTodoUseCaseRequest(long id, String title, Boolean done) {

        this.id = id;
        this.title = title;
        this.done = done;

        validate();
    }

    private void validate() {
        if (this.id <= 0)
            addErrorNotification(MsgUltil.IDENTIFIER_X0_IS_INVALID(null)[0],
                    MsgUltil.IDENTIFIER_X0_IS_INVALID(Long.toString(this.id))[1]);

        if (this.title == null || this.title.trim().isEmpty())
            addErrorNotification(MsgUltil.X0_IS_REQUIRED(null)[0],
                    MsgUltil.X0_IS_REQUIRED("title")[1]);
    }
}