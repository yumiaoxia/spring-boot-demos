package com.itsherman.commondto2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-24
 */
@Configuration
@EnableConfigurationProperties(DtoConfigProperties.class)
public class DtoConfig {

    @Autowired
    private DtoConfigProperties dtoConfigProperties;


}
