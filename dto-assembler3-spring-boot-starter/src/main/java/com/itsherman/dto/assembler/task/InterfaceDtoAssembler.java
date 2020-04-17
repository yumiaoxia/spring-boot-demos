package com.itsherman.dto.assembler.task;

public class InterfaceDtoAssembler<T, R> implements DtoAssembler<T, R> {


    @SafeVarargs
    @Override
    public final R assemble(Class<R> dtoClass, T... ts) {
        return (R) new DtoInterfaceProxy<T, R>().getInstance(dtoClass, ts);
    }


}
