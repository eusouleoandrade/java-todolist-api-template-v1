package com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

public class ResponseWithDataAndErrors<TData, TErrors> {
    public boolean succeeded;

    public String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public TErrors errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public TData data;

    public ResponseWithDataAndErrors(boolean succeeded, String message, TErrors errors, TData data) {
        this.succeeded = succeeded;
        this.errors = errors;
        this.data = data;

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

    public TErrors getErrors() {
        return errors;
    }

    public void setErrors(TErrors errors) {
        this.errors = errors;
    }

    public TData getData() {
        return data;
    }

    public void setData(TData data) {
        this.data = data;
    }
}
