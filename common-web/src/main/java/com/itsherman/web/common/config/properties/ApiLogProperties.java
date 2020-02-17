package com.itsherman.web.common.config.properties;

import com.itsherman.web.common.enums.ApiLogEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.common-web.api-log")
public class ApiLogProperties {

    private ApiLogEnum type = ApiLogEnum.EXCEPTION;

    public ApiLogEnum getType() {
        return type;
    }

    public void setType(ApiLogEnum type) {
        this.type = type;
    }
}
