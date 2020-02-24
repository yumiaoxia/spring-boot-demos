package com.itsherman.dto.assembler.manager;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

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
    public R assemble(T sourceValue) {
        return (R) sourceValue;
    }
}
