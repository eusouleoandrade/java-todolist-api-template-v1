package com.mycompany.javatodolistapitemplatev1.application.interfaces.repositories;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mycompany.javatodolistapitemplatev1.domain.entities.Todo;

public interface ITodoRepositoryAsync {
    CompletableFuture<List<Todo>> getTodoListAsync();
}
