package com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.javatodolistapitemplatev1.shared.notification.interfaces.INotifiableGeneric;

public abstract class NotifiableGeneric<TNotificationMessage>
        implements INotifiableGeneric<TNotificationMessage>, AutoCloseable {

    protected final List<TNotificationMessage> errorNotifications = new ArrayList<>();

    protected final List<TNotificationMessage> successNotifications = new ArrayList<>();

    public boolean hasErrorNotification() {
        return !errorNotifications.isEmpty();
    }

    public boolean hasSuccessNotification() {
        return !successNotifications.isEmpty();
    }

    public List<TNotificationMessage> getErrorNotifications() {
        return this.errorNotifications;
    }

    public List<TNotificationMessage> getSuccessNotifications() {
        return this.successNotifications;
    }

    public void addErrorNotification(TNotificationMessage notification) {
        errorNotifications.add(notification);
    }

    public void addErrorNotifications(List<TNotificationMessage> notifications) {
        if (!notifications.isEmpty()) {
            errorNotifications.addAll(notifications);
        }
    }

    public void addSuccessNotification(TNotificationMessage notification) {
        successNotifications.add(notification);
    }

    public void addSuccessNotifications(List<TNotificationMessage> notifications) {
        if (!notifications.isEmpty()) {
            successNotifications.addAll(notifications);
        }
    }

    @Override
    public void close() {
        errorNotifications.clear();
        successNotifications.clear();
    }
}