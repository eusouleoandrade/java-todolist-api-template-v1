package com.mycompany.javatodolistapitemplatev1.shared.notification.contexts;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class NotificationContextTest {

    @Test
    public void shouldInstantiateNotificationContext() {
        // Act
        NotificationContext context = new NotificationContext();

        // Assert
        assertThat(context).isNotNull();
    }
}
