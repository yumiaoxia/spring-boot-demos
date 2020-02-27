package com.itsherman.dto.assembler.utils;

import com.itsherman.dto.assembler.exception.DtoAssembleException;
import com.itsherman.dto.assembler.task.ClassDtoAssembler;
import com.itsherman.dto.assembler.task.DtoAssembler;
import com.itsherman.dto.assembler.task.InterfaceDtoAssembler;

public class DtoAssembleUtils {

    public static <T, R> R assemble(Class<R> dtoClass, T... ts) {
        DtoAssembler<T, R> dtoAssembler;
        if (dtoClass.isInterface()) {
            dtoAssembler = new InterfaceDtoAssembler<>();
        } else if (dtoClass.getSuperclass() != null) {
            dtoAssembler = new ClassDtoAssembler<>();
        } else {
            throw new DtoAssembleException(String.format("can not just dtoClass '%s',it is not interface and has not supperClass", dtoClass));
        }
        return dtoAssembler.assemble(dtoClass, ts);
    }
}
