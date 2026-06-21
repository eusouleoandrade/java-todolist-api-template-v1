package com.mycompany.javatodolistapitemplatev1.shared.notification.contexts;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class NotificationContextGenericTest {

    @Test
    public void shouldInstantiateNotificationContextGeneric() {
        // Act
        NotificationContextGeneric<String> context = new NotificationContextGeneric<>();

        // Assert
        assertThat(context).isNotNull();
    }
}
