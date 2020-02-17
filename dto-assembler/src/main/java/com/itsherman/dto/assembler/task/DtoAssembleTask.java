package com.itsherman.dto.assembler.task;

public interface DtoAssembleTask<T,R> {

    R assemble(Class<R> rClass, T... t);
}
