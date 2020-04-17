package com.itsherman.dto.assembler.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassPropertyDefinition extends ModelPropertyDefinition {

    private Field field;

    private Method writeMethod;

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
