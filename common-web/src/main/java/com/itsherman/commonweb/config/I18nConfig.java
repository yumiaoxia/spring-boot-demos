package com.itsherman.commonweb.config;

import com.itsherman.commonweb.interceptor.LocaleMessageInterceptor;
import com.itsherman.commonweb.locale.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-04
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

    @Bean
    public HandlerInterceptor localeMessageInterceptor() {
        return new LocaleMessageInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeMessageInterceptor());
    }
}
