package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.DtoDefinition;

public class InterfaceDtoAssembler<T, R> implements DtoAssembler<T, R> {


    @Override
    public R assemble(DtoDefinition dtoDefinition, T... ts) {
        R r = null;
        return (R) new DtoInterfaceProxy<T, R>().getInstance(r, dtoDefinition, ts);
    }


}
