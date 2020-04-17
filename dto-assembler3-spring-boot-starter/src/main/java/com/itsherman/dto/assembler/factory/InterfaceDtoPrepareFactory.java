package com.itsherman.dto.assembler.factory;

import com.itsherman.dto.assembler.handler.DtoModelRegister;
import com.itsherman.dto.assembler.handler.inter.InterDtoModelRegister;
import com.itsherman.dto.assembler.handler.inter.InterMethodMappingValidator;
import com.itsherman.dto.assembler.validator.DtoValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InterfaceDtoPrepareFactory extends DtoPrepareFactory {

    @Resource
    private InterDtoModelRegister dtoModelRegister;

    @Resource
    private InterMethodMappingValidator validator;

    @Override
    public DtoModelRegister getDtoModelRegister() {
        return dtoModelRegister;
    }

    @Override
    public DtoValidator getDtoValiddator() {
        return validator;
    }
}
