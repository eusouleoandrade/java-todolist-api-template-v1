package com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

public class ResponseWithData<TData> {

    public boolean succeeded;

    public String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> errors;

    public TData data;

    public ResponseWithData(TData data, boolean succeeded, String message, List<String> errors) {
        this.data = data;
        this.succeeded = succeeded;
        this.message = message;
        this.errors = errors;

        if (message == null || message.trim().isEmpty()) {
            this.message = succeeded ? MsgUltil.RESPONSE_SUCCEEDED_MESSAGE()[1]
                    : MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST()[1];
        }
    }

    public ResponseWithData(TData data, boolean succeeded, String message) {
        this(data, succeeded, message, null);
    }

    public ResponseWithData(TData data, boolean succeeded) {
        this(data, succeeded, null, null);
    }
}
