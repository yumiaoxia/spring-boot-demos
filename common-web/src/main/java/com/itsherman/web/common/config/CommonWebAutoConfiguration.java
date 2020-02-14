package com.itsherman.web.common.config;

import com.itsherman.web.common.exception.ExceptionController;
import com.itsherman.web.common.filter.ChanelFilter;
import com.itsherman.web.common.response.ResponseBodyHandlerAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@Import({DateTimeSerializerConfig.class, I18nConfig.class, SwaggerConfig.class})
public class CommonWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "exceptionController", annotation = ControllerAdvice.class)
    public ExceptionController exceptionController() {
        return new ExceptionController();
    }

    @Bean
    public ChanelFilter chanelFilter() {
        return new ChanelFilter();
    }

    @Bean
    public ResponseBodyHandlerAdvice responseBodyHandlerAdvice() {
        return new ResponseBodyHandlerAdvice();
    }
}
