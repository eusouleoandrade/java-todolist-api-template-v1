package com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories;

import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ITodoRepositoryAsync {

    CompletableFuture<List<Todo>> getTodoListAsync();

    CompletableFuture<List<Todo>> getPaginatedTodoListsAsync(int pageSize, int pageNumber);

    CompletableFuture<Integer> getTotalRecordsAsync();

    CompletableFuture<Todo> getAsync(long id);

    CompletableFuture<Todo> createAsync(Todo entity);

    CompletableFuture<Boolean> deleteAsync(long id);

    CompletableFuture<Boolean> updateAsync(Todo entity);
}