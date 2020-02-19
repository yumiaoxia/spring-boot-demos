package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.core.DtoDefinition;

import java.util.Optional;

public interface DtoAssembleTask<T,R> {

    Optional<R> assemble(DtoDefinition dtoDefinition, T... ts);
}
