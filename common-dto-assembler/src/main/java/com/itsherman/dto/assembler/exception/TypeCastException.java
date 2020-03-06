package com.itsherman.dto.assembler.exception;

public class TypeCastException extends DtoAssembleException {
    public TypeCastException() {
    }

    public TypeCastException(String message) {
        super(message);
    }

    public TypeCastException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeCastException(Throwable cause) {
        super(cause);
    }
}
