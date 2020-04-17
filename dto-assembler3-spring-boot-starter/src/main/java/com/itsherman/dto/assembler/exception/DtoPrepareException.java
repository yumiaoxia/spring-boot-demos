package com.itsherman.dto.assembler.exception;

public class DtoPrepareException extends DtoAssembleException {

    public DtoPrepareException() {
    }

    public DtoPrepareException(String message) {
        super(message);
    }

    public DtoPrepareException(String message, Throwable cause) {
        super(message, cause);
    }

    public DtoPrepareException(Throwable cause) {
        super(cause);
    }
}
