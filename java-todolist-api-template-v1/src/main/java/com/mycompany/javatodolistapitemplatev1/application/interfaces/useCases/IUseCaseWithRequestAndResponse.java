package com.mycompany.javatodolistapitemplatev1.application.interfaces.useCases;

import java.util.concurrent.CompletableFuture;

public interface IUseCaseWithRequestAndResponse<TRequest, TResponse> {

    CompletableFuture<TResponse> runAsync(TRequest request);
}