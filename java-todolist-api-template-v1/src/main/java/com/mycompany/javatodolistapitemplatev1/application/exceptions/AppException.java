package com.mycompany.javatodolistapitemplatev1.application.exceptions;

import java.text.MessageFormat;

public class AppException extends RuntimeException {

    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Object... args) {
        super(MessageFormat.format(message, args));
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}