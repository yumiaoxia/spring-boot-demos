package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.ClassDtoPropertyDefinition;
import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.DtoDefinitionHolder;
import com.itsherman.dto.assembler.core.DtoPropertyDefinition;
import com.itsherman.dto.assembler.utils.PropertyAssembleUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

public class ArrayClassPropertyAssembleTask<T, R> implements DtoPropertyAssembleTask<T, R> {

    @Override
    public void assemble(DtoPropertyDefinition definition, R r, T... ts) {
        ClassDtoPropertyDefinition classDtoPropertyDefinition = (ClassDtoPropertyDefinition) definition;
        Class<?> fieldType = classDtoPropertyDefinition.getField().getType();
        Class<?> componentType = fieldType.getComponentType();
        Method readMethod = classDtoPropertyDefinition.getReadMethod();
        Optional<T> first = Arrays.stream(ts)
                .filter(t -> t.getClass().isAssignableFrom(classDtoPropertyDefinition.getSourceClass()))
                .findFirst();
        if (!first.isPresent()) {
            throw new NullPointerException();
        }
        try {
            Object[] values = (Object[]) classDtoPropertyDefinition.getReadMethod().invoke(first.get());
            if (Modifier.isFinal(componentType.getModifiers()) || componentType.isPrimitive()) {
                classDtoPropertyDefinition.getWriteMethod().invoke(r, values);
            } else {
                DtoDefinition dtoDefinition = DtoDefinitionHolder.getDtoDefinitions().get(componentType);
                if (definition != null) {
                    Object[] result = new Object[values.length];
                    if (componentType.getSuperclass() != null) {
                        for (int i = 0; i < values.length; i++) {
                            ClassDtoAssembleTask<Object, Object> classDtoAssembleTask = new ClassDtoAssembleTask<>();
                            result[i] = classDtoAssembleTask.assemble((Class<Object>) componentType, values[i]);

                        }
                    }

                } else if (componentType.isArray()) {

                }
            }
            ClassDtoPropertyDefinition propertyDefinition = new ClassDtoPropertyDefinition(classDtoPropertyDefinition.getDtoDefinition());
            propertyDefinition.set
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        PropertyAssembleUtils.assemble(componentType, classDtoPropertyDefinition, r, ts);
    }
}
