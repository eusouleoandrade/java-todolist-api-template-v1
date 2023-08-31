package com.mycompany.javatodolistapitemplatev1.presentation.interceptors;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.javatodolistapitemplatev1.application.dtos.wrappers.Response;
import com.mycompany.javatodolistapitemplatev1.shared.notification.contexts.NotificationContext;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NotificationContextInterceptor implements HandlerInterceptor {

    private final NotificationContext notificationContext;
    private final Logger logger = LoggerFactory.getLogger(NotificationContextInterceptor.class);

    public NotificationContextInterceptor(NotificationContext notificationContext) {
        this.notificationContext = notificationContext;
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable ModelAndView modelAndView) throws IOException {

        if (notificationContext.hasErrorNotification()) {

            response.setContentType("application/json");

            boolean hasSingleErrorWithCodeCOD0004 = notificationContext.getErrorNotifications().size() == 1 &&
                    notificationContext.getErrorNotifications().get(0).getKey()
                            .equals(MsgUltil.DATA_OF_X0_X1_NOT_FOUND(null, null)[0]);

            if (hasSingleErrorWithCodeCOD0004)
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            else
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            Response errorResponse = new Response(false, notificationContext.getErrorNotifications());

            try {
                String notifications = new ObjectMapper().writeValueAsString(errorResponse);

                logger.warn("Finalizes request with notifications. Notifications: {}", notifications);

                response.getWriter().write(notifications);

            } catch (JsonProcessingException ex) {

                String errorMessage = "Error during response serialization: " + ex.getMessage();

                logger.error(errorMessage, ex);

                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

                response.getWriter().write("{\"message\":\"" + errorMessage + "\"}");

            } finally {
                response.getWriter().flush();
            }

            return;
        }
    }
}