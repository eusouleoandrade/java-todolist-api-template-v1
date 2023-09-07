package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import java.util.concurrent.CompletableFuture;

public interface IUseCaseWithResponse<TResponse> {

    CompletableFuture<TResponse> runAsync();
}
