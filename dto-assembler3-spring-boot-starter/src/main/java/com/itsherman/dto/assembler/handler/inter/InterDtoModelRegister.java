package com.itsherman.dto.assembler.handler.inter;

import com.itsherman.dto.assembler.constants.Commonconstants;
import com.itsherman.dto.assembler.core.InterfacePropertyDefinition;
import com.itsherman.dto.assembler.core.ModelDefinition;
import com.itsherman.dto.assembler.core.ModelPropertyDefinition;
import com.itsherman.dto.assembler.handler.DtoModelRegister;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InterDtoModelRegister extends DtoModelRegister {

    @Override
    public void doRegister(ModelDefinition md) {
        Method[] dtoMethods = md.getDtoClass().getMethods();
        List<ModelPropertyDefinition> ipds = Arrays.stream(dtoMethods).filter(dtoMethod ->
                dtoMethod.getName().startsWith(Commonconstants.GETTER_PREFIX) && !Modifier.isStatic(dtoMethod.getModifiers()))
                .map(method -> {
                    InterfacePropertyDefinition ipd = new InterfacePropertyDefinition();
                    ipd.setDtoMethod(method);
                    return ipd;
                }).collect(Collectors.toList());
        md.setModelProperties(ipds);
    }
}
