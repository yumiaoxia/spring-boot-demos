package com.itsherman.commonweb.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-03
 */
public class ApiRequest<T> implements Serializable {

    /**
     * 请求时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @JsonProperty
    private LocalDateTime requestTime = LocalDateTime.now();

    /**
     * 请求参数载荷
     */
    @NotNull
    @JsonProperty("data")
    private T commend;

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public T getCommend() {
        return commend;
    }

    public void setCommend(T commend) {
        this.commend = commend;
    }
}
