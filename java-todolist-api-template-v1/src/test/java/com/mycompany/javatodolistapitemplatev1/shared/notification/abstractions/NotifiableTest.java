package com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.mycompany.javatodolistapitemplatev1.shared.notification.models.NotificationMessage;

public class NotifiableTest {

    private static class ConcreteNotifiable extends Notifiable {}

    private ConcreteNotifiable notifiable;

    @BeforeEach
    public void setUp() {
        notifiable = new ConcreteNotifiable();
    }

    @DisplayName("Should add error notification with key and message")
    @Test
    public void shouldAddErrorNotificationWithKeyAndMessage() {
        // Arrange
        String key = "err_key";
        String message = "Error message";

        // Act
        notifiable.addErrorNotification(key, message);
        
        // Assert
        assertThat(notifiable.hasErrorNotification()).isTrue();
        assertThat(notifiable.getErrorNotifications()).hasSize(1);
        
        NotificationMessage msg = notifiable.getErrorNotifications().get(0);
        assertThat(msg.getKey()).isEqualTo(key);
        assertThat(msg.getMessage()).isEqualTo(message);
    }

    @DisplayName("Should add error notification with formatted message")
    @Test
    public void shouldAddErrorNotificationWithFormattedMessage() {
        // Arrange
        String key = "err_key";
        String format = "Error in field %s: %s";
        String param1 = "title";
        String param2 = "too long";

        // Act
        notifiable.addErrorNotification(key, format, param1, param2);
        
        // Assert
        assertThat(notifiable.hasErrorNotification()).isTrue();
        assertThat(notifiable.getErrorNotifications()).hasSize(1);
        
        NotificationMessage msg = notifiable.getErrorNotifications().get(0);
        assertThat(msg.getKey()).isEqualTo(key);
        assertThat(msg.getMessage()).isEqualTo("Error in field title: too long");
    }

    @DisplayName("Should add multiple error notifications from other Notifiable objects")
    @Test
    public void shouldAddMultipleErrorNotificationsFromOtherNotifiables() {
        // Arrange
        ConcreteNotifiable other1 = new ConcreteNotifiable();
        other1.addErrorNotification("k1", "m1");
        
        ConcreteNotifiable other2 = new ConcreteNotifiable();
        other2.addErrorNotification("k2", "m2");
        
        // Act
        notifiable.addErrorNotifications(other1, other2);
        
        // Assert
        assertThat(notifiable.getErrorNotifications()).hasSize(2);
        assertThat(notifiable.getErrorNotifications().get(0).getKey()).isEqualTo("k1");
        assertThat(notifiable.getErrorNotifications().get(1).getKey()).isEqualTo("k2");
    }

    @DisplayName("Should add success notification with key and message")
    @Test
    public void shouldAddSuccessNotificationWithKeyAndMessage() {
        // Arrange
        String key = "ok_key";
        String message = "Success message";

        // Act
        notifiable.addSuccessNotification(key, message);
        
        // Assert
        assertThat(notifiable.hasSuccessNotification()).isTrue();
        assertThat(notifiable.getSuccessNotifications()).hasSize(1);
        
        NotificationMessage msg = notifiable.getSuccessNotifications().get(0);
        assertThat(msg.getKey()).isEqualTo(key);
        assertThat(msg.getMessage()).isEqualTo(message);
    }

    @DisplayName("Should add success notification with formatted message")
    @Test
    public void shouldAddSuccessNotificationWithFormattedMessage() {
        // Arrange
        String key = "ok_key";
        String format = "Success in field %s: %s";
        String param1 = "status";
        String param2 = "updated";

        // Act
        notifiable.addSuccessNotification(key, format, param1, param2);
        
        // Assert
        assertThat(notifiable.hasSuccessNotification()).isTrue();
        assertThat(notifiable.getSuccessNotifications()).hasSize(1);
        
        NotificationMessage msg = notifiable.getSuccessNotifications().get(0);
        assertThat(msg.getKey()).isEqualTo(key);
        assertThat(msg.getMessage()).isEqualTo("Success in field status: updated");
    }

    @DisplayName("Should add multiple success notifications from other Notifiable objects")
    @Test
    public void shouldAddMultipleSuccessNotificationsFromOtherNotifiables() {
        // Arrange
        ConcreteNotifiable other1 = new ConcreteNotifiable();
        other1.addSuccessNotification("k1", "m1");
        
        ConcreteNotifiable other2 = new ConcreteNotifiable();
        other2.addSuccessNotification("k2", "m2");
        
        // Act
        notifiable.addSuccessNotifications(other1, other2);
        
        // Assert
        assertThat(notifiable.getSuccessNotifications()).hasSize(2);
        assertThat(notifiable.getSuccessNotifications().get(0).getKey()).isEqualTo("k1");
        assertThat(notifiable.getSuccessNotifications().get(1).getKey()).isEqualTo("k2");
    }
}
