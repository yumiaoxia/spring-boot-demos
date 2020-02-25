package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.DtoDefinition;

public interface DtoAssembler<T, R> {

    R assemble(DtoDefinition dtoDefinition, T... ts);
}
