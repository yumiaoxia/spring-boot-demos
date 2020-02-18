package com.itsherman.dto.assembler.utils;

import com.itsherman.dto.assembler.core.DtoPropertyDefinition;
import com.itsherman.dto.assembler.task.ArrayClassPropertyAssembleTask;
import com.itsherman.dto.assembler.task.DtoPropertyAssembleTask;
import com.itsherman.dto.assembler.task.FinalClassDtoPropertyAssembleTask;

import java.lang.reflect.Modifier;

public class PropertyAssembleUtils {

    public static <T, R> void assemble(Class<?> fieldType, DtoPropertyDefinition definition, R r, T... ts) {
        if (Modifier.isFinal(fieldType.getModifiers())) {
            DtoPropertyAssembleTask<T, R> propertyAssembleTask = new FinalClassDtoPropertyAssembleTask<>();
            propertyAssembleTask.assemble(definition, r, ts);
        } else if (fieldType.isArray()) {
            DtoPropertyAssembleTask<T, R> propertyAssembleTask = new ArrayClassPropertyAssembleTask<>();
            propertyAssembleTask.assemble(definition, r, ts);
        }
    }
}
