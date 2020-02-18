package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.DtoPropertyDefinition;

public interface DtoPropertyAssembleTask<T, R> {

    void assemble(DtoPropertyDefinition definition, R r, T... ts);
}
