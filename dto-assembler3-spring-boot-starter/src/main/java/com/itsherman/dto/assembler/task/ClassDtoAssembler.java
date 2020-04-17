package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.ClassDtoPropertyDefinition;
import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.DtoPropertyDefinition;
import com.itsherman.dto.assembler.exception.DtoAssembleException;
import com.itsherman.dto.assembler.utils.CollectionUtils;
import com.itsherman.dto.assembler.utils.DtoAssembleUtils;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class ClassDtoAssembler<T, R> implements DtoAssembler<T, R> {

    @SafeVarargs
    @Override
    public final R assemble(Class<R> dtoClass, T... ts) {
        R r;
        try {
            r = dtoClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DtoAssembleException();
        }
        DtoDefinition dtoDefinition = DtoDefinitionHolder.getDtoDefinitions().get(dtoClass);
        if (dtoDefinition == null) {
            throw new DtoAssembleException(String.format("Can not found any dtoDefinition for dtoClass '%s'", dtoClass.getName()));
        }

        Set<DtoPropertyDefinition> validPropertyDefinitions = dtoDefinition.getValidPropertyDefinitions();
        for (DtoPropertyDefinition validPropertyDefinition : validPropertyDefinitions) {
            ClassDtoPropertyDefinition propertyDefinition = (ClassDtoPropertyDefinition) validPropertyDefinition;
            Optional<T> first = Arrays.stream(ts).filter(t -> t.getClass().isAssignableFrom(propertyDefinition.getSourceClass())).findFirst();
            if (!first.isPresent()) {
                throw new DtoAssembleException(String.format("Illegal Source Object,it required sourceClass '%s',but not found", propertyDefinition.getSourceClass()));
            }
            Method writeMethod = propertyDefinition.getWriteMethod();
            Parameter parameter = writeMethod.getParameters()[0];
            Type writeMethodParameterType = parameter.getParameterizedType();

            Method readMethod = propertyDefinition.getReadMethod();
            Object readMethodValue;
            try {
                readMethodValue = readMethod.invoke(first.get());
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new DtoAssembleException(e);
            }
            Object writeMethodValue;
            try {
                if (readMethodValue != null) {
                    writeMethodValue = doAssemble(writeMethodParameterType, readMethodValue);
                    if (writeMethodValue != null) {
                        writeMethod.invoke(r, writeMethodValue);
                    }
                }
            } catch (RuntimeException | InvocationTargetException | IllegalAccessException e) {
                throw new DtoAssembleException(String.format("无法编集 %s。 目标类型：%s, 原因：%s", propertyDefinition.getField().getName(), dtoClass, e.getMessage()), e);
            }
        }
        return r;
    }

    private Object doAssemble(Type writeMethodParameterType, Object sourceReturnValue) {
        if (writeMethodParameterType instanceof Class) {
            Class<?> parameterClass = (Class) writeMethodParameterType;
            if (parameterClass.isArray()) {
                Class<?> componentType = parameterClass.getComponentType();
                Object[] readMethodValues = (Object[]) sourceReturnValue;
                Object writeMethodParameterValues = Array.newInstance(componentType, readMethodValues.length);
                for (int i = 0; i < readMethodValues.length; i++) {
                    Object readMethodValue = readMethodValues[i];
                    Array.set(writeMethodParameterValues, i, readMethodValue == null ? null : doAssemble(componentType, readMethodValue));
                }
                return writeMethodParameterValues;
            } else if (parameterClass.isPrimitive() || Modifier.isFinal(parameterClass.getModifiers())) {
                return sourceReturnValue;
            } else if (DtoDefinitionHolder.getDtoDefinitions().get(parameterClass) != null) {
                return sourceReturnValue == null ? null : DtoAssembleUtils.assemble(parameterClass, sourceReturnValue);
            }
        } else if (writeMethodParameterType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) writeMethodParameterType;
            Type rawType = parameterizedType.getRawType();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (rawType instanceof Class) {
                Class<?> rawClass = (Class) rawType;
                if (Collection.class.isAssignableFrom(rawClass)) {
                    Collection<Object> sourceValues = (Collection) sourceReturnValue;
                    Collection<Object> parameterValues = CollectionUtils.emptyCollection(parameterizedType);
                    for (Object sourceValue : sourceValues) {
                        if (sourceValue != null) {
                            Object parameterVal = doAssemble(actualTypeArguments[0], sourceValue);
                            parameterValues.add(parameterVal);
                        }
                    }
                    return parameterValues;
                }
            }
        }
        throw new DtoAssembleException(String.format("Type match Error,target type '%s'", writeMethodParameterType));
    }
}
