package com.mycompany.javatodolistapitemplatev1;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProgramTest {

    @DisplayName("Should construct Program instance")
    @Test
    public void shouldConstructProgram() {
        // Act
        Program program = new Program();

        // Assert
        assertThat(program).isNotNull();
    }

    @DisplayName("Should run Program main method in non-web mode successfully")
    @Test
    public void shouldRunProgramMain() {
        // Arrange
        // Use non-web mode to prevent the embedded web server (Tomcat) from blocking the test execution.
        String[] args = new String[]{
            "--spring.main.web-application-type=none",
            "--spring.main.banner-mode=off",
            "--spring.main.lazy-initialization=true"
        };

        // Act & Assert
        // The method should run and complete successfully without throwing exceptions
        Program.main(args);
    }
}
