package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import java.util.concurrent.CompletableFuture;

public interface IUseCaseWithoutRequest<TResponse> {

    CompletableFuture<TResponse> runAsync();
}
