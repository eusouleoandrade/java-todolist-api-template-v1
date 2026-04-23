package com.mycompany.javatodolistapitemplatev1.presentation.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        homeController = new HomeController();
    }

    @DisplayName("Should return redirect to swagger-ui.html")
    @Test
    public void shouldReturnRedirectToSwaggerUi() throws Exception {

        // Arrange
        // Act - make request to root path
        // Assert
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/swagger-ui.html"));
    }

    @DisplayName("Should return swagger-ui redirect on home method")
    @Test
    public void shouldReturnSwaggerUiRedirectOnHomeMethod() {

        // Arrange
        // Act
        String result = homeController.home();

        // Assert
        org.assertj.core.api.Assertions.assertThat(result)
                .isEqualTo("redirect:/swagger-ui.html");
    }

    @DisplayName("Should accept GET request to home endpoint")
    @Test
    public void shouldAcceptGetRequestToHomeEndpoint() throws Exception {

        // Arrange
        // Act - make GET request
        // Assert
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @DisplayName("Should return correct redirect location")
    @Test
    public void shouldReturnCorrectRedirectLocation() throws Exception {

        // Arrange
        // Act
        // Assert
        mockMvc.perform(get("/"))
                .andExpect(redirectedUrl("/swagger-ui.html"));
    }

    @DisplayName("Home controller should be accessible")
    @Test
    public void homeControllerShouldBeAccessible() {

        // Arrange
        // Act
        String redirectUrl = homeController.home();

        // Assert
        org.assertj.core.api.Assertions.assertThat(redirectUrl)
                .isNotNull()
                .isNotEmpty()
                .contains("swagger-ui.html");
    }
}

