package com.itsherman.web.common.exception;

public class ServiceException extends Exception {

    private String errorCode;

    public ServiceException() {
    }

    public ServiceException(String errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
