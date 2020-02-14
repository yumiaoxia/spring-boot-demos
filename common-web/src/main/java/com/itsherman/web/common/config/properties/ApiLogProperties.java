package com.itsherman.web.common.config.properties;

import com.itsherman.web.common.enums.ApiLogEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.common-web.api-log")
public class ApiLogProperties {

    private ApiLogEnum apiLogEnum = ApiLogEnum.EXCEPTION;

    public ApiLogEnum getApiLogEnum() {
        return apiLogEnum;
    }

    public void setApiLogEnum(ApiLogEnum apiLogEnum) {
        this.apiLogEnum = apiLogEnum;
    }
}
