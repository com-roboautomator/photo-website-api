package com.roboautomator.app.component.config.logging;

import lombok.AllArgsConstructor;

import com.roboautomator.app.component.middleware.CorrelationMiddleware;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class CorrelationConfig implements WebMvcConfigurer {

    private CorrelationMiddleware correlationMiddleware;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(correlationMiddleware);
    }
}
