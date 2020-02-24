package com.itsherman.dto.assembler.manager;

import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.DtoDefinitionHolder;
import com.itsherman.dto.assembler.core.DtoPropertyDefinition;
import com.itsherman.dto.assembler.core.InterfaceDtoPropertyDefinition;
import com.itsherman.dto.assembler.utils.DtoAssembleUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;

public class RawPropertyAssembleTask<T, R> implements PropertyAssembleTask<T, R> {

    private PropertyAssembleManager propertyAssembleManager;

    private Class<R> dtoClass;

    @Override
    public boolean check(Type targetType) {
        if (targetType instanceof Class) {
            if (null != DtoDefinitionHolder.getDtoDefinitions().get(dtoClass)) {
                dtoClass = (Class<R>) targetType;
                return true;
            }
        }
        return false;
    }

    @Override
    public R assemble(T sourceValue) {
        DtoDefinition dtoDefinition = DtoDefinitionHolder.getDtoDefinitions().get(dtoClass);
        Set<DtoPropertyDefinition> validPropertyDefinitions = dtoDefinition.getValidPropertyDefinitions();
        for (DtoPropertyDefinition validPropertyDefinition : validPropertyDefinitions) {
            if (dtoClass.isInterface()) {
                InterfaceDtoPropertyDefinition interfaceDtoPropertyDefinition = (InterfaceDtoPropertyDefinition) validPropertyDefinition;
                Method dtoMethod = interfaceDtoPropertyDefinition.getDtoMethod();
                return (R) DtoAssembleUtils.assemble(dtoClass, sourceValue);
            }
        }
        return null;
    }
}
