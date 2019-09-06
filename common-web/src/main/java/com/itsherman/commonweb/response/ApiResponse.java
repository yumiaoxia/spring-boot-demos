package com.itsherman.commonweb.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itsherman.commonweb.constants.CommonConstants;
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
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = -7416528189137018359L;

    @ApiModelProperty(value = "响应时间", example = CommonConstants.DATETIME_NOW)
    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime responseTime = LocalDateTime.now();

    @ApiModelProperty("请求是否成功")
    @JsonProperty(defaultValue = "true")
    private Boolean success;

    @ApiModelProperty("响应结果消息")
    @JsonProperty
    private String message;

    @ApiModelProperty("返回数据主体，载荷")
    @JsonProperty
    private T data;

    @ApiModelProperty("响应结果编码")
    @JsonProperty
    private String code;

    public static <T> ApiResponse<T> createSuccess() {
        return createSuccess(null);
    }

    public static <T> ApiResponse<T> createSuccess(T t) {
        return createSuccess("0", t);
    }

    public static <T> ApiResponse<T> createSuccess(String code, T t) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setCode(code);
        apiResponse.setData(t);
        return apiResponse;
    }

    public static <T> ApiResponse<T> createError() {
        return createError("9999");
    }

    public static <T> ApiResponse<T> createError(String code) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(false);
        apiResponse.setCode(code);
        return apiResponse;
    }

    public LocalDateTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime = responseTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "responseTime=" + responseTime +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", code='" + code + '\'' +
                '}';
    }
}
