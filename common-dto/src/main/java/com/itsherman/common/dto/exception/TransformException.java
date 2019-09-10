package com.itsherman.common.dto.exception;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-10
 */
public class TransformException extends RuntimeException {
    private String message;

    public TransformException() {
    }

    public TransformException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
