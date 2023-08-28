package com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers;

import java.util.List;

import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

public class Response<TData> {

    public boolean succeeded;

    public String message;

    public List<String> errors;

    public TData data;

    public Response(TData data, boolean succeeded, String message, List<String> errors) {
        this.data = data;
        this.succeeded = succeeded;
        this.message = message;
        this.errors = errors;

        if (message == null || message.trim().isEmpty()) {
            this.message = succeeded ? MsgUltil.RESPONSE_SUCCEEDED_MESSAGE_TXT()
                    : MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST()[1];
        }
    }

    public Response(TData data, boolean succeeded, String message) {
        this(data, succeeded, message, null);
    }
}
