package com.itsherman.dto.assembler.core;

import java.lang.reflect.Method;

public class InterfacePropertyDefinition extends ModelPropertyDefinition {

    private Method dtoMethod;

    public Method getDtoMethod() {
        return dtoMethod;
    }

    public void setDtoMethod(Method dtoMethod) {
        this.dtoMethod = dtoMethod;
    }
}
