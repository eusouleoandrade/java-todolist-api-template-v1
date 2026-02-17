package com.mycompany.javatodolistapitemplatev1.application.dtos.requests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetPaginatedTodoListsUseCaseRequestTest {

    private static final int maxPageSize = 50;
    private static final int defaultPageSize = 10;
    private static final int initialPagination = 1;

    @DisplayName("Should execute successfully")
    @ParameterizedTest
    @CsvSource({
            "1, 20",
            "2, 49",
            "3, 50"
    })
    public void shouldExecuteSuccessfully(int pageNumber, int pageSize) {

        // Arrange
        GetPaginatedTodoListsUseCaseRequest request;

        // Act
        request = new GetPaginatedTodoListsUseCaseRequest(
                pageNumber,
                pageSize,
                maxPageSize, defaultPageSize, initialPagination);

        // Assert
        assertNotNull(request);

        assertEquals(pageNumber, request.getPageNumber());

        assertEquals(pageSize, request.getPageSize());
    }

    @DisplayName("Should execute successfully when the maximum page size is larger than configured")
    @ParameterizedTest
    @CsvSource({
            "1, 51",
            "2, 60",
            "3, 100"
    })
    public void shouldExecuteSuccessfully_WhenTheMaximumPageSizeIsLargerThanConfigured(int pageNumber,
            int pageSize) {

        // Arrange
        GetPaginatedTodoListsUseCaseRequest request;

        // Act
        request = new GetPaginatedTodoListsUseCaseRequest(
                pageNumber,
                pageSize,
                maxPageSize, defaultPageSize, initialPagination);

        // Assert
        assertNotNull(request);

        assertEquals(pageNumber, request.getPageNumber());

        assertEquals(maxPageSize, request.getPageSize());
        assertNotEquals(pageSize, request.getPageSize());
    }

    @DisplayName("Should execute successfully when the page number is invalid")
    @ParameterizedTest
    @CsvSource({
            "0, 20",
            "-1, 20",
            "-10, 20"
    })
    public void shouldExecuteSuccessfully_WhenThePageNumberIsInvalid(int pageNumber, int pageSize) {

        // Arrange
        GetPaginatedTodoListsUseCaseRequest request;

        // Act
        request = new GetPaginatedTodoListsUseCaseRequest(
                pageNumber,
                pageSize,
                maxPageSize, defaultPageSize, initialPagination);

        // Assert
        assertNotNull(request);

        assertEquals(initialPagination, request.getPageNumber());
        assertNotEquals(pageNumber, request.getPageNumber());

        assertEquals(pageSize, request.getPageSize());
    }

    @DisplayName("Should execute successfully when the pageSize is invalid")
    @ParameterizedTest
    @CsvSource({
            "1, -1",
            "2, -10",
            "3, 0"
    })
    public void shouldExecuteSuccessfully_WhenThePageSizeIsInvalid(int pageNumber, int pageSize) {

        // Arrange
        GetPaginatedTodoListsUseCaseRequest request;

        // Act
        request = new GetPaginatedTodoListsUseCaseRequest(
                pageNumber,
                pageSize,
                maxPageSize, defaultPageSize, initialPagination);

        // Assert
        assertNotNull(request);

        assertEquals(pageNumber, request.getPageNumber());

        assertEquals(defaultPageSize, request.getPageSize());
        assertNotEquals(pageSize, request.getPageSize());
    }
}