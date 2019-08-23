package com.itsherman.common.email.response;

import lombok.Data;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-08-23
 */
@Data
public class ResultMessage {
    private Boolean success;
    private String operName;
    private String flag;
    private String message;

    public ResultMessage(Boolean success, String operName, String flag, String message) {
        this.success = success;
        this.operName = operName;
        this.flag = flag;
        this.message = message;
    }
}
