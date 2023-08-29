package com.mycompany.javatodolistapitemplatev1.shared.notification.interfaces;

import java.util.List;

public interface INotifiableGeneric<TNotificationMessage> {

    // Properties
    boolean hasErrorNotification();

    boolean hasSuccessNotification();

    List<TNotificationMessage> getErrorNotifications();

    List<TNotificationMessage> getSuccessNotifications();
}
