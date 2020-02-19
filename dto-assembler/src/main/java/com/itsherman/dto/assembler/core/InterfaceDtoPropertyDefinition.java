package com.itsherman.dto.assembler.core;

import java.lang.reflect.Method;

public class InterfaceDtoPropertyDefinition extends DtoPropertyDefinition {


    private Method dtoMethod;

    public InterfaceDtoPropertyDefinition(DtoDefinition dtoDefinition, Method dtoMethod) {
        super(dtoDefinition);
        this.dtoMethod = dtoMethod;
    }


    public Method getDtoMethod() {
        return dtoMethod;
    }

    public void setDtoMethod(Method dtoMethod) {
        this.dtoMethod = dtoMethod;
    }
}
