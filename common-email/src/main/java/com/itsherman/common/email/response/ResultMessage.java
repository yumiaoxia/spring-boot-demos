package com.itsherman.common.email.response;

import lombok.Data;
import lombok.ToString;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
@Data
@ToString
public class ResultMessage<T> {
    private Boolean success;
    private String operName;
    private String flag;
    private String message;
    private T data;

    public ResultMessage(Boolean success, String operName, String flag, String message) {
        this(success, operName, flag, message, null);
    }

    public ResultMessage(Boolean success, String operName, String flag, String message, T data) {
        this.success = success;
        this.operName = operName;
        this.flag = flag;
        this.message = message;
        this.data = data;
    }
}
