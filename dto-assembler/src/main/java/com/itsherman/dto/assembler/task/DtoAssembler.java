package com.itsherman.dto.assembler.task;

public interface DtoAssembler<T, R> {

    R assemble(Class<R> dtoClass, T... ts);
}
