package com.roboautomator.app.component.config.logging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.roboautomator.app.component.middleware.LoggingMiddleware;

@ExtendWith(MockitoExtension.class)
class LoggingConfigTest {

    @Mock
    private LoggingMiddleware loggingMiddleware;

    @Mock
    private InterceptorRegistry interceptorRegistry;

    private LoggingConfig loggingConfig;

    @BeforeEach
    void setUpLoggingConfig() {
        loggingConfig = new LoggingConfig(loggingMiddleware);
    }

    @Test
    void shouldAddLoggingMiddlewareToInteceptorRegistry() {

        loggingConfig.addInterceptors(interceptorRegistry);

        verify(interceptorRegistry, times(1))
                .addInterceptor(loggingMiddleware);
    }
}