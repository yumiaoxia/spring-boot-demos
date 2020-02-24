package com.itsherman.dto.assembler.manager;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

public class ArrayPropertyAssembleTask<T, R> implements PropertyAssembleTask<T, R> {

    private Class<R> targetClass;

    @Resource
    private PropertyAssembleManager propertyAssembleManager;

    @Override
    public boolean check(Type targetType) {
        if (targetType instanceof Class) {
            this.targetClass = (Class) targetType;
            return targetClass.isArray();
        }
        return false;
    }

    @Override
    public R assemble(T sourceValue) {
        Object[] sourceArray = (Object[]) sourceValue;
        Class<?> componentType = targetClass.getComponentType();
        Object targetArray = Array.newInstance(componentType, sourceArray.length);
        for (int i = 0; i < sourceArray.length; i++) {
            Object source = sourceArray[i];
            Array.set(targetArray, i, source == null ? null : propertyAssembleManager.doAssemble(componentType, source));
        }
        return (R) targetArray;
    }
}
