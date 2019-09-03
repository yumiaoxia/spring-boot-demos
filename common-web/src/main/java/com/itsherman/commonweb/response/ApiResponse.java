package com.itsherman.commonweb.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-03
 */
public class ApiResponse<T> {

    @JsonProperty
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime responseTime;

    @JsonProperty
    private Boolean success;

    @JsonProperty
    private String message;

    @JsonProperty
    private T data;

    @JsonProperty
    private String code;

    public static <T> ApiResponse createSuccess() {
        return createSuccess(null);
    }

    public static <T> ApiResponse createSuccess(T t) {
        return createSuccess("0", "", t);
    }

    public static <T> ApiResponse createSuccess(String code, String message, T t) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setCode(code);
        apiResponse.setData(t);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static ApiResponse createError() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setCode("9999");
        apiResponse.setMessage("系统错误");
        return apiResponse;
    }

    public static ApiResponse createError(String code, String message) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setCode(code);
        apiResponse.setMessage(message);
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
}
