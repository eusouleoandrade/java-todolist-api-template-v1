package com.mycompany.javatodolistapitemplatev1.shared.notification.models;

public class NotificationMessage {

    public String key;

    public String message;

    public NotificationMessage(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
