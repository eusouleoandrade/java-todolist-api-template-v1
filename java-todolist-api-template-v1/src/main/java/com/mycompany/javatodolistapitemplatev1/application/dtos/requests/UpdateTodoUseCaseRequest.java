package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.Notifiable;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

public class UpdateTodoUseCaseRequest extends Notifiable {

    private long id;

    private String title;

    private Boolean done;

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
                    MsgUltil.X0_IS_REQUIRED("Title")[1]);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getDone() {
        return done;
    }
}