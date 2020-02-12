package com.itsherman.web.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itsherman.web.common.constants.CommonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-03
 */
@ApiModel
public class ApiRequest<T> implements Serializable {
    private static final long serialVersionUID = 7373793858406747388L;

    /**
     * 请求时间
     */
    @ApiModelProperty(value = "请求时间", example = CommonConstants.DATETIME_NOW)
    @JsonProperty
    private LocalDateTime requestTime = LocalDateTime.now();

    /**
     * 请求参数载荷
     */
    @ApiModelProperty("请求参数体，载荷")
    @JsonProperty("data")
    private T command;

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public T getCommand() {
        return command;
    }

    public void setCommand(T command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "ApiRequest{" +
                "requestTime=" + requestTime +
                ", commend=" + command +
                '}';
    }
}
