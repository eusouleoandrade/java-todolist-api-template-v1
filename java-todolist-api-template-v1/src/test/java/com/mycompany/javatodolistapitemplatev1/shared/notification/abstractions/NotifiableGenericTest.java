package com.mycompany.javatodolistapitemplatev1.shared.notification.abstractions;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NotifiableGenericTest {

    private static class ConcreteNotifiableGeneric<T> extends NotifiableGeneric<T> {}

    private ConcreteNotifiableGeneric<String> notifiable;

    @BeforeEach
    public void setUp() {
        notifiable = new ConcreteNotifiableGeneric<>();
    }

    @DisplayName("Should initially have no notifications")
    @Test
    public void shouldInitiallyHaveNoNotifications() {
        // Act
        boolean hasError = notifiable.hasErrorNotification();
        boolean hasSuccess = notifiable.hasSuccessNotification();
        var errors = notifiable.getErrorNotifications();
        var successes = notifiable.getSuccessNotifications();

        // Assert
        assertThat(hasError).isFalse();
        assertThat(hasSuccess).isFalse();
        assertThat(errors).isEmpty();
        assertThat(successes).isEmpty();
    }

    @DisplayName("Should add single error notification")
    @Test
    public void shouldAddSingleErrorNotification() {
        // Arrange
        String error = "Error 1";

        // Act
        notifiable.addErrorNotification(error);

        // Assert
        assertThat(notifiable.hasErrorNotification()).isTrue();
        assertThat(notifiable.getErrorNotifications()).containsExactly(error);
    }

    @DisplayName("Should add multiple error notifications")
    @Test
    public void shouldAddMultipleErrorNotifications() {
        // Arrange
        var errors = List.of("Error 1", "Error 2");

        // Act
        notifiable.addErrorNotifications(errors);

        // Assert
        assertThat(notifiable.hasErrorNotification()).isTrue();
        assertThat(notifiable.getErrorNotifications()).containsExactly("Error 1", "Error 2");
    }

    @DisplayName("Should not add empty error notifications list")
    @Test
    public void shouldNotAddEmptyErrorNotificationsList() {
        // Arrange
        var emptyList = List.<String>of();

        // Act
        notifiable.addErrorNotifications(emptyList);

        // Assert
        assertThat(notifiable.hasErrorNotification()).isFalse();
    }

    @DisplayName("Should add single success notification")
    @Test
    public void shouldAddSingleSuccessNotification() {
        // Arrange
        String success = "Success 1";

        // Act
        notifiable.addSuccessNotification(success);

        // Assert
        assertThat(notifiable.hasSuccessNotification()).isTrue();
        assertThat(notifiable.getSuccessNotifications()).containsExactly(success);
    }

    @DisplayName("Should add multiple success notifications")
    @Test
    public void shouldAddMultipleSuccessNotifications() {
        // Arrange
        var successes = List.of("Success 1", "Success 2");

        // Act
        notifiable.addSuccessNotifications(successes);

        // Assert
        assertThat(notifiable.hasSuccessNotification()).isTrue();
        assertThat(notifiable.getSuccessNotifications()).containsExactly("Success 1", "Success 2");
    }

    @DisplayName("Should not add empty success notifications list")
    @Test
    public void shouldNotAddEmptySuccessNotificationsList() {
        // Arrange
        var emptyList = List.<String>of();

        // Act
        notifiable.addSuccessNotifications(emptyList);

        // Assert
        assertThat(notifiable.hasSuccessNotification()).isFalse();
    }

    @DisplayName("Should clear notifications on close")
    @Test
    public void shouldClearNotificationsOnClose() {
        // Arrange
        notifiable.addErrorNotification("Error 1");
        notifiable.addSuccessNotification("Success 1");
        
        // Act
        notifiable.close();
        
        // Assert
        assertThat(notifiable.hasErrorNotification()).isFalse();
        assertThat(notifiable.hasSuccessNotification()).isFalse();
        assertThat(notifiable.getErrorNotifications()).isEmpty();
        assertThat(notifiable.getSuccessNotifications()).isEmpty();
    }
}
