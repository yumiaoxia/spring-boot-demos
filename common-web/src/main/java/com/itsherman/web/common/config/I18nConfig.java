package com.itsherman.web.common.config;

import com.itsherman.web.common.locale.BundleMessageProperties;
import com.itsherman.web.common.locale.MyLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-04
 */
@EnableConfigurationProperties(BundleMessageProperties.class)
@Configuration
public class I18nConfig implements WebMvcConfigurer {

    @Autowired
    private BundleMessageProperties bundleMessageProperties;

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(bundleMessageProperties.getEncoding().name());
        messageSource.setBasename(bundleMessageProperties.getBasename());
        messageSource.setCacheSeconds(3);
        return messageSource;
    }
}
