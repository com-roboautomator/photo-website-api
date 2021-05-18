package com.roboautomator.app.component.config.logging;

import lombok.AllArgsConstructor;

import com.roboautomator.app.component.middleware.LoggingMiddleware;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class LoggingConfig implements WebMvcConfigurer {

    private LoggingMiddleware loggingMiddleware;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingMiddleware);
    }
}