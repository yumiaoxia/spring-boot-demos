package com.itsherman.dto.assembler.task;

import java.util.function.Function;

public class ArrayDtoAssembleTask<T,R> implements DtoAssembleTask<T,R> {


    @Override
    public Function<T, R> assemble(R r) {

        return new Function<T, R>() {
            @Override
            public R apply(T t) {
                Class<?> aClass = r.getClass();

                return null;
            }
        };
    }
}
