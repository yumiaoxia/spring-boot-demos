package com.itsherman.dto.assembler.factory;

import com.itsherman.dto.assembler.handler.DtoModelRegister;
import com.itsherman.dto.assembler.utils.SpringUtils;
import com.itsherman.dto.assembler.validator.AbstractDtoValidator;

public abstract class DtoPrepareFactory {


    public static DtoPrepareFactory of(Class<?> dtoClass) {
        DtoPrepareFactory dtoPrepareFactory = null;
        if (dtoClass.isInterface()) {
            dtoPrepareFactory = SpringUtils.getBean(InterfaceDtoPrepareFactory.class);
        } else {
            dtoPrepareFactory = SpringUtils.getBean(ClassDtoPrepareFactory.class);
        }
        return dtoPrepareFactory;
    }


    public abstract DtoModelRegister getDtoModelRegister();

    public abstract AbstractDtoValidator getDtoValiddator();
}
