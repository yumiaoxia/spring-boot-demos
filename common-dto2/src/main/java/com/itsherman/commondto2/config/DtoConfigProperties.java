package com.itsherman.commondto2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-24
 */
@Component
@ConfigurationProperties(prefix = "spring.dto")
public class DtoConfigProperties {

    private String[] basePackeges;

    public String[] getBasePackeges() {
        return basePackeges;
    }

    public void setBasePackeges(String[] basePackeges) {
        this.basePackeges = basePackeges;
    }
}
