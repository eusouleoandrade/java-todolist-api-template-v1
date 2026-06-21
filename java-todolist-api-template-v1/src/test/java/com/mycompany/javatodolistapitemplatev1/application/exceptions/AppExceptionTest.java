package com.mycompany.javatodolistapitemplatev1.application.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AppExceptionTest {

    @DisplayName("Should create exception with default constructor")
    @Test
    public void shouldCreateWithDefaultConstructor() {
        // Act
        AppException exception = new AppException();

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNull();
        assertThat(exception.getCause()).isNull();
    }

    @DisplayName("Should create exception with message constructor")
    @Test
    public void shouldCreateWithMessageConstructor() {
        // Arrange
        String message = "Error occurred";

        // Act
        AppException exception = new AppException(message);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isNull();
    }

    @DisplayName("Should create exception with formatted message and arguments")
    @Test
    public void shouldCreateWithFormattedMessageAndArgs() {
        // Arrange
        String pattern = "Error on {0} with code {1}";
        String arg1 = "User";
        int arg2 = 404;

        // Act
        AppException exception = new AppException(pattern, arg1, arg2);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Error on User with code 404");
        assertThat(exception.getCause()).isNull();
    }

    @DisplayName("Should create exception with message and cause constructor")
    @Test
    public void shouldCreateWithMessageAndCauseConstructor() {
        // Arrange
        String message = "Outer error";
        Throwable cause = new IllegalArgumentException("Invalid argument");

        // Act
        AppException exception = new AppException(message, cause);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
