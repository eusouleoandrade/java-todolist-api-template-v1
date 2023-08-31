package com.mycompany.javatodolistapitemplatev1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mycompany.javatodolistapitemplatev1.presentation.interceptors.NotificationContextInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final NotificationContextInterceptor notificationContextInterceptor;

    public InterceptorConfig(NotificationContextInterceptor notificationContextInterceptor) {
        this.notificationContextInterceptor = notificationContextInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(notificationContextInterceptor);
    }
}