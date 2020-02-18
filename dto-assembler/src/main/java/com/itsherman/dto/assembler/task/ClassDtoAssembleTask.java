package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.ClassDtoPropertyDefinition;
import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.DtoDefinitionHolder;
import com.itsherman.dto.assembler.core.DtoPropertyDefinition;

import java.util.Set;

public class ClassDtoAssembleTask<T,R> implements DtoAssembleTask<T,R>{

    @Override
    public R assemble(Class<R> rClass, T... ts) {
        R r = null;
        DtoDefinition dtoDefinition = DtoDefinitionHolder.getDtoDefinitions().get(rClass);
        if (rClass.getSuperclass() != null) {
            try {
                r = rClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            Set<DtoPropertyDefinition> validPropertyDefinitions = dtoDefinition.getValidPropertyDefinition();
            for (DtoPropertyDefinition validPropertyDefinition : validPropertyDefinitions) {
                ClassDtoPropertyDefinition classDtoPropertyDefinition = (ClassDtoPropertyDefinition) validPropertyDefinition;
                Class fieldType = classDtoPropertyDefinition.getField().getType();

            }
        }
        return r;
    }
}
