package com.itsherman.dto.assembler.core;

import java.lang.reflect.Method;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoPropertyDefinition {

    private DtoDefinition dtoDefinition;
    private Class sourceClass;
    private Method readMethod;

    public DtoPropertyDefinition(DtoDefinition dtoDefinition) {
        if (dtoDefinition == null) {
            throw new NullPointerException();
        }
        this.dtoDefinition = dtoDefinition;
    }

    public DtoDefinition getDtoDefinition() {
        return dtoDefinition;
    }

    public void setDtoDefinition(DtoDefinition dtoDefinition) {
        this.dtoDefinition = dtoDefinition;
    }

    public Method getReadMethod() {
        return readMethod;
    }

    public void setReadMethod(Method readMethod) {
        this.readMethod = readMethod;
    }

    public Class getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class sourceClass) {
        this.sourceClass = sourceClass;
    }
}
