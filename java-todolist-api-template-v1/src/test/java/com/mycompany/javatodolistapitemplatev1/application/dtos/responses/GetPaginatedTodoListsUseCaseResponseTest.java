package com.mycompany.javatodolistapitemplatev1.application.dtos.responses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetPaginatedTodoListsUseCaseResponseTest {

    private static final int pageNumber = 1;
    private static final int pageSize = 50;
    private static final int totalPages = 3;
    private static final int totalRecords = 130;

    @DisplayName("Should execute successfully when to use the parameterized constructor")
    @ParameterizedTest
    @CsvSource({
            "1, Fazer compras., true",
            "2, Efetuar investimentos., false",
            "3, Analisar planejamento financeiro., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseTheParameterizedCtor(long id, String title, boolean done) {

        // Arranje
        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(id);
        todoUseCaseResponse.setTitle(title);
        todoUseCaseResponse.setDone(done);

        var todoUseCaseResponseList = new ArrayList<TodoUseCaseResponse>();
        todoUseCaseResponseList.add(todoUseCaseResponse);

        // Act
        GetPaginatedTodoListsUseCaseResponse response = new GetPaginatedTodoListsUseCaseResponse(pageNumber, pageSize,
                totalPages, totalRecords, todoUseCaseResponseList);

        // Assert
        assertNotNull(response);

        assertEquals(pageNumber, response.getPageNumber());
        assertEquals(pageSize, response.getPageSize());
        assertEquals(totalPages, response.getTotalPages());
        assertEquals(totalRecords, response.getTotalRecords());

        assertThat(response.getTodoListUseCaseResponse()).isNotEmpty();
        assertThat(response.getTodoListUseCaseResponse()).hasSize(1);

        assertEquals(todoUseCaseResponseList, response.getTodoListUseCaseResponse());
    }

    @DisplayName("Should execute successfully when to use the setters")
    @ParameterizedTest
    @CsvSource({
            "1, Fazer compras., true",
            "2, Efetuar investimentos., false",
            "3, Analisar planejamento financeiro., true"
    })
    public void shouldExecuteSuccessfully_WhenToUseSetters(long id, String title, boolean done) {

        // Arranje
        var todoUseCaseResponse = new TodoUseCaseResponse();
        todoUseCaseResponse.setId(id);
        todoUseCaseResponse.setTitle(title);
        todoUseCaseResponse.setDone(done);

        var todoUseCaseResponseList = new ArrayList<TodoUseCaseResponse>();
        todoUseCaseResponseList.add(todoUseCaseResponse);

        // Act
        GetPaginatedTodoListsUseCaseResponse response = new GetPaginatedTodoListsUseCaseResponse(0, 0,
                0, 0, null);

        response.setPageNumber(pageNumber);
        response.setPageSize(pageSize);
        response.setTotalPages(totalPages);
        response.setTotalRecords(totalRecords);
        response.setTodoListUseCaseResponse(todoUseCaseResponseList);

        // Assert
        assertNotNull(response);

        assertEquals(pageNumber, response.getPageNumber());
        assertEquals(pageSize, response.getPageSize());
        assertEquals(totalPages, response.getTotalPages());
        assertEquals(totalRecords, response.getTotalRecords());

        assertThat(response.getTodoListUseCaseResponse()).isNotEmpty();
        assertThat(response.getTodoListUseCaseResponse()).hasSize(1);

        assertEquals(todoUseCaseResponseList, response.getTodoListUseCaseResponse());
    }
}