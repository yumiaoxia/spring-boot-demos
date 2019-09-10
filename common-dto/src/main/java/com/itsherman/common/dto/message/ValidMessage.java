package com.itsherman.common.dto.message;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-10
 */
public class ValidMessage {

    private boolean flag;

    private String message;

    public ValidMessage() {
    }

    public ValidMessage(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
