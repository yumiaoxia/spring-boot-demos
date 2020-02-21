package com.itsherman.dto.assembler.manager;

import java.lang.reflect.Type;
import java.util.Optional;

public interface PropertyAssembleTask<T, R> {

    boolean check(Type targetType);


    Optional<R> assemble(T sourceValue);
}
