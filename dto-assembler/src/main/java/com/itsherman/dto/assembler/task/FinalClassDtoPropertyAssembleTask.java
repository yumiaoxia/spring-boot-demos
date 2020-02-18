package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.ClassDtoPropertyDefinition;
import com.itsherman.dto.assembler.core.DtoPropertyDefinition;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

public class FinalClassDtoPropertyAssembleTask<T, R> implements DtoPropertyAssembleTask<T, R> {

    @Override
    public void assemble(DtoPropertyDefinition definition, R r, T... ts) {
        ClassDtoPropertyDefinition classDtoPropertyDefinition = (ClassDtoPropertyDefinition) definition;
        Optional<T> first = Arrays.stream(ts)
                .filter(t -> t.getClass().isAssignableFrom(classDtoPropertyDefinition.getSourceClass()))
                .findFirst();
        if (!first.isPresent()) {
            throw new NullPointerException();
        }
        try {
            Object value = classDtoPropertyDefinition.getReadMethod().invoke(first.get());
            classDtoPropertyDefinition.getWriteMethod().invoke(r, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
