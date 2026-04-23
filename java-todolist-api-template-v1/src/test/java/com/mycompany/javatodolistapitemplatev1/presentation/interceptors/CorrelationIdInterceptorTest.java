package com.mycompany.javatodolistapitemplatev1.presentation.interceptors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest
public class CorrelationIdInterceptorTest {

    private CorrelationIdInterceptor interceptor;

    @BeforeEach
    public void setUp() {
        interceptor = new CorrelationIdInterceptor();
        MDC.clear();
    }

    @DisplayName("Should generate Correlation-Id when header is not provided")
    @Test
    public void shouldGenerateCorrelationIdWhenHeaderNotProvided() {

        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();

        // Act
        boolean result = interceptor.preHandle(request, response, null);

        // Assert
        assertThat(result).isTrue();

        String correlationId = MDC.get("CorrelationId");
        assertNotNull(correlationId);
        assertThat(correlationId).isNotEmpty();

        // Verify it's a valid UUID format
        assertThat(UUID.fromString(correlationId)).isNotNull();
    }

    @DisplayName("Should use existing Correlation-Id from header")
    @Test
    public void shouldUseExistingCorrelationIdFromHeader() {

        // Arrange
        String expectedCorrelationId = "550e8400-e29b-41d4-a716-446655440000";
        var request = new MockHttpServletRequest();
        request.addHeader("Correlation-Id", expectedCorrelationId);
        var response = new MockHttpServletResponse();

        // Act
        boolean result = interceptor.preHandle(request, response, null);

        // Assert
        assertThat(result).isTrue();
        String correlationId = MDC.get("CorrelationId");
        assertThat(correlationId).isEqualTo(expectedCorrelationId);
    }

    @DisplayName("Should generate Correlation-Id when header is empty")
    @Test
    public void shouldGenerateCorrelationIdWhenHeaderIsEmpty() {

        // Arrange
        var request = new MockHttpServletRequest();
        request.addHeader("Correlation-Id", "");
        var response = new MockHttpServletResponse();

        // Act
        boolean result = interceptor.preHandle(request, response, null);

        // Assert
        assertThat(result).isTrue();
        String correlationId = MDC.get("CorrelationId");
        assertNotNull(correlationId);
        assertThat(correlationId).isNotEmpty();
    }

    @DisplayName("Should clear Correlation-Id after request handling")
    @Test
    public void shouldClearCorrelationIdAfterRequestHandling() {

        // Arrange
        String correlationId = "550e8400-e29b-41d4-a716-446655440000";
        MDC.put("CorrelationId", correlationId);
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();

        // Act
        interceptor.postHandle(request, response, null, null);

        // Assert
        String mdcValue = MDC.get("CorrelationId");
        assertThat(mdcValue).isNull();
    }

    @DisplayName("Should return true from preHandle to continue request processing")
    @Test
    public void shouldReturnTrueFromPreHandle() {

        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();

        // Act
        boolean result = interceptor.preHandle(request, response, null);

        // Assert
        assertThat(result).isTrue();
    }

    @DisplayName("Should handle Correlation-Id with special characters")
    @Test
    public void shouldHandleCorrelationIdWithSpecialCharacters() {

        // Arrange
        String specialId = "test-correlation-id-12345";
        var request = new MockHttpServletRequest();
        request.addHeader("Correlation-Id", specialId);
        var response = new MockHttpServletResponse();

        // Act
        boolean result = interceptor.preHandle(request, response, null);

        // Assert
        assertThat(result).isTrue();
        String correlationId = MDC.get("CorrelationId");
        assertThat(correlationId).isEqualTo(specialId);
    }

    @DisplayName("Should work with case-sensitive header")
    @Test
    public void shouldWorkWithCaseSensitiveHeader() {

        // Arrange
        String correlationId = "custom-correlation-id";
        var request = new MockHttpServletRequest();
        request.addHeader("Correlation-Id", correlationId);
        var response = new MockHttpServletResponse();

        // Act
        boolean result = interceptor.preHandle(request, response, null);

        // Assert
        assertThat(result).isTrue();
        String mdcValue = MDC.get("CorrelationId");
        assertThat(mdcValue).isEqualTo(correlationId);
    }
}