package com.mycompany.javatodolistapitemplatev1.presentation.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.javatodolistapitemplatev1.application.dtos.responses.NotificationMessagesResponse;
import com.mycompany.javatodolistapitemplatev1.shared.exceptions.AppException;
import com.mycompany.javatodolistapitemplatev1.shared.notification.models.NotificationMessage;
import com.mycompany.javatodolistapitemplatev1.shared.ultils.MsgUltil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ErrorHandlerFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(ErrorHandlerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {

            chain.doFilter(request, response);

        } catch (Exception ex) {

            logger.error("Finalizes request with errors.", ex);

            List<NotificationMessage> exceptionNotifications = new ArrayList<>();

            HttpServletResponse httpResponse = (HttpServletResponse) response;

            if (ex.getCause() instanceof AppException) {

                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                exceptionNotifications
                        .add(new NotificationMessage(MsgUltil.BAD_REQUEST()[0], ex.getCause().getMessage()));

            } else {

                httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                exceptionNotifications.add(new NotificationMessage(MsgUltil.INTERNAL_SERVER_ERROR()[0],
                        MsgUltil.INTERNAL_SERVER_ERROR()[1]));
            }

            var errorResponse = new NotificationMessagesResponse(exceptionNotifications);

            String serializedResponse = new ObjectMapper().writeValueAsString(errorResponse);

            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(serializedResponse);
        }
    }
}