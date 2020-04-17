package com.itsherman.dto.assembler.validator;

import com.itsherman.dto.assembler.core.ModelDefinition;

public abstract class AbstractDtoValidator implements DtoValidator {

    private ModelDefinition md;

    public ModelDefinition getMd() {
        return md;
    }

    public void setMd(ModelDefinition md) {
        this.md = md;
    }
}
