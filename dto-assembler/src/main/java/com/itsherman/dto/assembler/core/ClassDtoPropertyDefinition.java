package com.itsherman.dto.assembler.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassDtoPropertyDefinition extends DtoPropertyDefinition {

    private Field field;
    private Method writeMethod;

    public ClassDtoPropertyDefinition(DtoDefinition dtoDefinition) {
        super(dtoDefinition);
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getWriteMethod() {
        return writeMethod;
    }

    public void setWriteMethod(Method writeMethod) {
        this.writeMethod = writeMethod;
    }


}
