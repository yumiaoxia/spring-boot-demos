package com.itsherman.dto.assembler.manager;

import java.lang.reflect.Type;

public interface PropertyAssembleTask<T, R> {

    boolean check(Type targetType);


    R assemble(T sourceValue);
}
