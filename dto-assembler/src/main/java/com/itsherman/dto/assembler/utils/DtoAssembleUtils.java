package com.itsherman.dto.assembler.utils;

import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.DtoDefinitionHolder;
import com.itsherman.dto.assembler.exception.DtoAssembleException;
import com.itsherman.dto.assembler.task.ClassDtoAssembler;
import com.itsherman.dto.assembler.task.DtoAssembleTask;
import com.itsherman.dto.assembler.task.InterfaceDtoAssembler;

import java.util.Optional;

public class DtoAssembleUtils {

    public static <T, R> Optional<R> assemble(Class<R> dtoClass, T... ts) {
        DtoDefinition dtoDefinition = DtoDefinitionHolder.getDtoDefinitions().get(dtoClass);
        if (dtoDefinition == null) {
            throw new DtoAssembleException(String.format("can not found an 'dtodefinition' for %s", dtoClass));
        }
        DtoAssembleTask<T, R> dtoAssembleTask;
        if (dtoClass.isInterface()) {
            dtoAssembleTask = new InterfaceDtoAssembler<>();
        } else if (dtoClass.getSuperclass() != null) {
            dtoAssembleTask = new ClassDtoAssembler<>();
        } else {
            throw new DtoAssembleException(String.format("can not just dtoClass '%s',it is not interface and has not supperClass", dtoClass));
        }
        return dtoAssembleTask.assemble(dtoDefinition, ts);
    }
}
