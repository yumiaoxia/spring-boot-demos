package com.itsherman.dto.assembler.factory;

import com.itsherman.dto.assembler.handler.DtoModelRegister;
import com.itsherman.dto.assembler.handler.clazz.ClassDtoModelRegister;
import com.itsherman.dto.assembler.handler.clazz.ClassFieldMappingValidator;
import com.itsherman.dto.assembler.validator.DtoValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ClassDtoPrepareFactory extends DtoPrepareFactory {

    @Resource
    private ClassDtoModelRegister dtoModelRegister;

    @Resource
    private ClassFieldMappingValidator validator;


    @Override
    public DtoModelRegister getDtoModelRegister() {
        return dtoModelRegister;
    }

    @Override
    public DtoValidator getDtoValiddator() {
        return validator;
    }
}
