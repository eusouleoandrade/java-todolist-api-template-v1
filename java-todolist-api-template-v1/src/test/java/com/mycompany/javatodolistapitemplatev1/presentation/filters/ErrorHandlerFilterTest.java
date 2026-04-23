package com.mycompany.javatodolistapitemplatev1.presentation.filters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.mycompany.javatodolistapitemplatev1.application.exceptions.AppException;

import nl.altindag.log.LogCaptor;

@SpringBootTest
public class ErrorHandlerFilterTest {

    private ErrorHandlerFilter filter;
    private LogCaptor logCaptor;

    @BeforeEach
    public void setUp() {
        filter = new ErrorHandlerFilter();
        logCaptor = LogCaptor.forClass(ErrorHandlerFilter.class);
    }

    @DisplayName("Should pass request through filter successfully")
    @Test
    public void shouldPassRequestThroughFilterSuccessfully() throws Exception {

        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var chain = new MockFilterChain();

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @DisplayName("Should handle AppException with BAD_REQUEST status")
    @Test
    public void shouldHandleAppExceptionWithBadRequestStatus() throws Exception {

        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var appException = new AppException("Invalid input");
        var chain = new MockFilterChain() {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response)
                    throws jakarta.servlet.ServletException {
                throw new jakarta.servlet.ServletException(appException);
            }
        };

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        assertEquals(400, response.getStatus());
        assertThat(response.getContentType()).isEqualTo("application/json");

        // Verify response contains error message
        var responseBody = response.getContentAsString();
        assertThat(responseBody).isNotEmpty();
        assertThat(responseBody).contains("COD0011");
    }

    @DisplayName("Should handle generic exception with INTERNAL_SERVER_ERROR status")
    @Test
    public void shouldHandleGenericExceptionWithInternalServerErrorStatus() throws Exception {

        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var runtimeException = new RuntimeException("Unexpected error");
        var chain = new MockFilterChain() {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response)
                    throws jakarta.servlet.ServletException {
                throw runtimeException;
            }
        };

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        assertEquals(500, response.getStatus());
        assertThat(response.getContentType()).isEqualTo("application/json");

        // Verify response contains error message
        var responseBody = response.getContentAsString();
        assertThat(responseBody).isNotEmpty();
        assertThat(responseBody).contains("COD0001");
    }

    @DisplayName("Should set content type to application/json on error")
    @Test
    public void shouldSetContentTypeToApplicationJsonOnError() throws Exception {

        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var chain = new MockFilterChain() {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response)
                    throws jakarta.servlet.ServletException {
                throw new RuntimeException("Test error");
            }
        };

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        assertThat(response.getContentType()).isEqualTo("application/json");
    }

    @DisplayName("Should log error when exception occurs")
    @Test
    public void shouldLogErrorWhenExceptionOccurs() throws Exception {

        // Arrange
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var chain = new MockFilterChain() {
            @Override
            public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response)
                    throws jakarta.servlet.ServletException {
                throw new RuntimeException("Test error");
            }
        };

        // Act
        filter.doFilter(request, response, chain);

        // Assert
        assertThat(logCaptor.getErrorLogs()).isNotEmpty();
        assertThat(logCaptor.getErrorLogs().getFirst()).contains("Finalizes request with errors");
    }
}