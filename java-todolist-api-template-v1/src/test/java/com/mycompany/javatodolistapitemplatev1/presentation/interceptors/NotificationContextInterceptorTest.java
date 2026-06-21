package com.mycompany.javatodolistapitemplatev1.presentation.interceptors;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.mycompany.javatodolistapitemplatev1.shared.notification.contexts.NotificationContext;
import com.mycompany.javatodolistapitemplatev1.shared.notification.models.NotificationMessage;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

@SpringBootTest
public class NotificationContextInterceptorTest {

    private NotificationContext notificationContext;
    private NotificationContextInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        notificationContext = new NotificationContext();
        interceptor = new NotificationContextInterceptor(notificationContext);
    }

    @DisplayName("Should do nothing when there are no error notifications")
    @Test
    public void shouldDoNothingWhenNoErrors() throws IOException {
        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();

        // Act
        interceptor.postHandle(request, response, null, null);

        // Assert
        assertThat(response.getContentType()).isNull();
        assertThat(response.getStatus()).isEqualTo(200); // remains default OK
        assertThat(response.getContentAsString()).isEmpty();
    }

    @DisplayName("Should return 404 when there is a single error with key COD0004")
    @Test
    public void shouldReturn404ForSingleNotFoundError() throws IOException {
        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        
        String keyCOD0004 = MsgUltil.DATA_OF_X0_X1_NOT_FOUND("Todo", "1")[0]; // "COD0004"
        notificationContext.addErrorNotification(keyCOD0004, "Todo not found");

        // Act
        interceptor.postHandle(request, response, null, null);

        // Assert
        assertThat(response.getContentType()).isEqualTo("application/json");
        assertThat(response.getStatus()).isEqualTo(404);
        assertThat(response.getContentAsString()).contains(keyCOD0004);
        assertThat(response.getContentAsString()).contains("Todo not found");
    }

    @DisplayName("Should return 400 when there is a single error but with non-COD0004 key")
    @Test
    public void shouldReturn400ForSingleNonNotFoundError() throws IOException {
        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        
        notificationContext.addErrorNotification("COD0002", "Title is required");

        // Act
        interceptor.postHandle(request, response, null, null);

        // Assert
        assertThat(response.getContentType()).isEqualTo("application/json");
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getContentAsString()).contains("COD0002");
        assertThat(response.getContentAsString()).contains("Title is required");
    }

    @DisplayName("Should return 400 when there are multiple error notifications")
    @Test
    public void shouldReturn400ForMultipleErrors() throws IOException {
        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        
        notificationContext.addErrorNotification("COD0002", "Title is required");
        notificationContext.addErrorNotification("COD0004", "Todo not found");

        // Act
        interceptor.postHandle(request, response, null, null);

        // Assert
        assertThat(response.getContentType()).isEqualTo("application/json");
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getContentAsString()).contains("COD0002");
        assertThat(response.getContentAsString()).contains("COD0004");
    }

    // A custom notification message subclass that throws an exception when Jackson attempts to read a property.
    private static class ThrowingNotificationMessage extends NotificationMessage {
        public ThrowingNotificationMessage() {
            super("key", "message");
        }

        @Override
        public String getMessage() {
            throw new RuntimeException("Serialization failure simulation");
        }
    }

    @DisplayName("Should return 500 when Jackson serialization fails")
    @Test
    public void shouldReturn500WhenSerializationFails() throws IOException {
        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();

        // Inject the throwing message into the notification context
        notificationContext.addErrorNotification(new ThrowingNotificationMessage());

        // Act
        interceptor.postHandle(request, response, null, null);

        // Assert
        assertThat(response.getContentType()).isEqualTo("application/json");
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getContentAsString()).contains("Error during response serialization");
    }
}
