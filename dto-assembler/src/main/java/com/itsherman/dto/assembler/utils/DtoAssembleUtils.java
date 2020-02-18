package com.itsherman.dto.assembler.utils;

import com.itsherman.dto.assembler.core.DtoDefinition;

public class DtoAssembleUtils {

    public static <T, R> R assemble(DtoDefinition dtoDefinition, R r, T... ts) {
        Class<?> dtoClass = dtoDefinition.getDtoClass();


    }
}
