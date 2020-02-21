package com.itsherman.dto.assembler.manager;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Optional;

public class SimplePropertyAssembleTask<T, R> implements PropertyAssembleTask<T, R> {

    private Class<R> targetClass;

    @Override
    public boolean check(Type targetType) {
        if (targetType instanceof Class) {
            targetClass = (Class<R>) targetType;
            if (targetClass.isPrimitive() || Modifier.isFinal(targetClass.getModifiers())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<R> assemble(T sourceValue) {
        return Optional.of((R) sourceValue);
    }
}
