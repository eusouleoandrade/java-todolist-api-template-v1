package com.mycompany.javatodolistapitemplatev1.shared.notification.contexts;

import com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions.NotifiableGeneric;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class NotificationContextGeneric<TNotificationMessage> extends NotifiableGeneric<TNotificationMessage> {
}
