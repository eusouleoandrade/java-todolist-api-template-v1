package com.mycompany.javatodolistapitemplatev1.application.usecases;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import com.mycompany.javatodolistapitemplatev1.application.dtos.requests.GetPaginatedTodoListsUseCaseRequest;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.GetPaginatedTodoListsUseCaseResponse;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories.ITodoRepositoryAsync;
import com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases.IGetPaginatedTodoListsUseCase;
import com.mycompany.javatodolistapitemplatev1.application.mappers.TodoMapper;

@Service
@RequestScope
public class GetPaginatedTodoListsUseCase implements IGetPaginatedTodoListsUseCase {

    private final Logger logger = LoggerFactory.getLogger(GetPaginatedTodoListsUseCase.class);

    private final ITodoRepositoryAsync todoRepositoryAsync;

    private final TodoMapper todoMapper;

    public GetPaginatedTodoListsUseCase(ITodoRepositoryAsync todoRepositoryAsync, TodoMapper todoMapper) {
        this.todoRepositoryAsync = todoRepositoryAsync;
        this.todoMapper = todoMapper;
    }

    @Override
    public CompletableFuture<GetPaginatedTodoListsUseCaseResponse> runAsync(
            GetPaginatedTodoListsUseCaseRequest request) {

        logger.info(String.format("Start useCase %s > method runAsync.",
                GetPaginatedTodoListsUseCase.class.getSimpleName()));

        var entities = todoRepositoryAsync
                .getPaginatedTodoListsAsync(request.getPageSize(), request.getPageNumber())
                .join();

        int totalRecords = todoRepositoryAsync.getTotalRecordsAsync().join();

        int totalPages = calculateTotalPages(totalRecords, request.getPageSize());

        var useCaseResponse = new GetPaginatedTodoListsUseCaseResponse(
                request.getPageNumber(),
                request.getPageSize(),
                totalPages,
                totalRecords,
                entities.stream().map(todoMapper::convertTodoUseCaseResponse)
                        .collect(Collectors.toList()));

        logger.info(String.format("Finishes successfully useCase %s > method runAsync.",
                GetPaginatedTodoListsUseCase.class.getSimpleName()));

        return CompletableFuture.completedFuture(useCaseResponse);
    }

    private int calculateTotalPages(int totalRecords, int pageSize) {

        double totalPages = ((double) totalRecords / (double) pageSize);
        return (int) Math.ceil(totalPages);
    }
}
