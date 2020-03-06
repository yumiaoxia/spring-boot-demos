package com.itsherman.dto.assembler.exception;

public class DtoAssembleException extends RuntimeException {

    public DtoAssembleException() {
    }

    public DtoAssembleException(String message) {
        super(message);
    }

    public DtoAssembleException(String message, Throwable cause) {
        super(message, cause);
    }

    public DtoAssembleException(Throwable cause) {
        super(cause);
    }
}
