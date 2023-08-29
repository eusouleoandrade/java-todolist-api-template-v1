package com.mycompany.javatodolistapitemplatev1.shared.notification.contexts;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.NotifiableGeneric;

@Component
@RequestScope
public class NotificationContextGeneric<TNotificationMessage> extends NotifiableGeneric<TNotificationMessage> {
}
