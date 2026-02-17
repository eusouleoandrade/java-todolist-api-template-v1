package com.mycompany.javatodolistapitemplatev1.presentation.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public class CorrelationIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // Extract the Correlation-Id from the request header
        String correlationId = request.getHeader("Correlation-Id");

        if (correlationId == null || correlationId.isEmpty()) {

            // Creates the Correlation-Id if it is not sent in the request header
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put("CorrelationId", correlationId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) {

        // Clear MDC after request processing
        MDC.remove("CorrelationId");
    }
}
