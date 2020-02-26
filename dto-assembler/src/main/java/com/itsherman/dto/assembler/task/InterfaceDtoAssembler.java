package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.DtoDefinition;

public class InterfaceDtoAssembler<T, R> implements DtoAssembler<T, R> {


    @Override
    public R assemble(DtoDefinition dtoDefinition, T... ts) {
        return (R) new DtoInterfaceProxy<T, R>().getInstance(dtoDefinition, ts);
    }


}
