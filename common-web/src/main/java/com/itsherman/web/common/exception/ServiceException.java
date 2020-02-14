package com.itsherman.web.common.exception;

public class ServiceException extends RuntimeException {

    private String errorCode;

    private Object[] variables;


    private ServiceException(String errorCode, Throwable cause, Object... variables) {
        super(errorCode, cause);
        this.errorCode = errorCode;
        this.variables = variables;
    }

    public static ServiceException of(String errorCode, Object... variables) {
        return ServiceException.of(errorCode, null, variables);
    }

    public static ServiceException of(String errorCode, Throwable cause, Object... variables) {
        return new ServiceException(errorCode, cause, variables);
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getVariables() {
        return variables;
    }

    public void setVariables(Object[] variables) {
        this.variables = variables;
    }
}
