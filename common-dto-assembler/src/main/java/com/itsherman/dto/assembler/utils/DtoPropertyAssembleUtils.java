package com.itsherman.dto.assembler.utils;

import com.itsherman.dto.assembler.core.DtoDefinitionHolder;
import com.itsherman.dto.assembler.exception.TypeCastException;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

public class DtoPropertyAssembleUtils {

    public static Object doAssemble(Type dtoReturnType, Object sourceReturnValue) {
        if (dtoReturnType instanceof Class) {
            Class<?> dtoReturnClass = (Class) dtoReturnType;
            // 判断返回类型
            if (dtoReturnClass.isArray()) {
                Object[] sourceArray = (Object[]) sourceReturnValue;
                Class<?> componentType = dtoReturnClass.getComponentType();
                Object dtoArray = Array.newInstance(componentType, sourceArray.length);
                for (int i = 0; i < sourceArray.length; i++) {
                    Object sourceValue = sourceArray[i];
                    Array.set(dtoArray, i, sourceValue == null ? null : sourceValue == null ? null : doAssemble(componentType, sourceValue));
                }
                return dtoArray;
            } else if (DtoDefinitionHolder.getDtoDefinitions().get(dtoReturnType) != null) {
                return DtoAssembleUtils.assemble(dtoReturnClass, sourceReturnValue);
            } else if (Modifier.isFinal(dtoReturnClass.getModifiers()) || dtoReturnClass.isPrimitive()) {
                return sourceReturnValue;
            }
        } else if (dtoReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedReturnType = (ParameterizedType) dtoReturnType;
            Type[] actualTypeArguments = parameterizedReturnType.getActualTypeArguments();
            if (Collection.class.isAssignableFrom((Class) parameterizedReturnType.getRawType())) {
                Collection<?> sourceReturnValues = (Collection<?>) sourceReturnValue;
                Collection<Object> dtoReturnValues = CollectionUtils.emptyCollection(parameterizedReturnType);
                for (Object returnValue : sourceReturnValues) {
                    if (returnValue != null) {
                        Object proxy = doAssemble(actualTypeArguments[0], returnValue);
                        Collections.addAll(dtoReturnValues, proxy);
                    }
                }
                return dtoReturnValues;
            }
        }
        throw new TypeCastException(String.format("Type match Error,target type '%s'", dtoReturnType));
    }
}
