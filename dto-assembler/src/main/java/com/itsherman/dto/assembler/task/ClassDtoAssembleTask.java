package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.ClassDtoPropertyDefinition;
import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.DtoDefinitionHolder;
import com.itsherman.dto.assembler.core.DtoPropertyDefinition;
import com.itsherman.dto.assembler.exception.DtoAssembleException;
import com.itsherman.dto.assembler.exception.TypeCastException;
import com.itsherman.dto.assembler.utils.DtoAssembleUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;

public class ClassDtoAssembleTask<T,R> implements DtoAssembleTask<T,R>{

    @SafeVarargs
    @Override
    public final Optional<R> assemble(DtoDefinition definition, T... ts) {
        R r;
        try {
            r = (R) definition.getDtoClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DtoAssembleException();
        }

        Set<DtoPropertyDefinition> validPropertyDefinitions = definition.getValidPropertyDefinition();
        for (DtoPropertyDefinition validPropertyDefinition : validPropertyDefinitions) {
            ClassDtoPropertyDefinition propertyDefinition = (ClassDtoPropertyDefinition) validPropertyDefinition;
            Optional<T> first = Arrays.stream(ts).filter(t -> t.getClass().isAssignableFrom(propertyDefinition.getSourceClass())).findFirst();
            if (!first.isPresent()) {
                throw new DtoAssembleException(String.format("Illegal Source Object,it required sourceClass '%s',but not found", propertyDefinition.getSourceClass()));
            }

            Method writeMethod = propertyDefinition.getWriteMethod();
            Parameter parameter = writeMethod.getParameters()[0];
            Class<?> writeMethodParameterType = parameter.getType();

            Method readMethod = propertyDefinition.getReadMethod();
            Object readMethodValue;
            try {
                readMethodValue = readMethod.invoke(first.get());
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new DtoAssembleException(e);
            }
            Object writeMethodValue;
            try {
                writeMethodValue = doAssemble(writeMethodParameterType, readMethodValue);
            } catch (RuntimeException e) {
                throw new DtoAssembleException(String.format("无法编集 %s。 目标类型：%s, 原因：%s", propertyDefinition.getField().getName(), definition.getDtoClass(), e.getMessage()), e);
            }
            try {
                writeMethod.invoke(r, writeMethodValue);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new DtoAssembleException(e);
            }
        }
        return Optional.of(r);
    }

    private Object doAssemble(Class<?> writeMethodParameterType, Object sourceReturnValue) {
        Object writeMethodValue = null;
        if (writeMethodParameterType != null) {
            if (writeMethodParameterType.isPrimitive() || Modifier.isFinal(writeMethodParameterType.getModifiers())) {
                writeMethodValue = sourceReturnValue;
            } else if (DtoDefinitionHolder.getDtoDefinitions().get(writeMethodParameterType) != null) {
                writeMethodValue = DtoAssembleUtils.assemble(writeMethodParameterType, sourceReturnValue);
            } else if (writeMethodParameterType.isArray()) {
                Class<?> componentType = writeMethodParameterType.getComponentType();
                Object[] readMethodValues = (Object[]) sourceReturnValue;
                Object[] writeMethodParameterValues = new Object[readMethodValues.length];
                for (int i = 0; i < readMethodValues.length; i++) {
                    writeMethodParameterValues[i] = doAssemble(componentType, readMethodValues[i]);
                }
                writeMethodValue = writeMethodParameterType;
            } else if (writeMethodParameterType.isAssignableFrom(Collection.class)) {
                Collection<Object> readMethodValues = (Collection<Object>) sourceReturnValue;
                Collection<Object> writeMethodParameterValues = Collections.emptyList();
                for (Object readMethodValue : readMethodValues) {
                    writeMethodParameterValues.add(doAssemble(writeMethodParameterType.getTypeParameters()[0].getGenericDeclaration(), readMethodValue));
                }
                writeMethodValue = writeMethodParameterValues;
            } else {
                throw new TypeCastException(String.format("Type match Error,parameterType type '%s',parameter '%s'", writeMethodParameterType, sourceReturnValue));
            }
        }
        return writeMethodValue;
    }
}
