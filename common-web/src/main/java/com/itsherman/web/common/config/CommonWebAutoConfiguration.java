package com.itsherman.web.common.config;

import com.itsherman.web.common.config.properties.ApiLogProperties;
import com.itsherman.web.common.exception.ExceptionController;
import com.itsherman.web.common.filter.ChanelFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@EnableConfigurationProperties(ApiLogProperties.class)
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

}
