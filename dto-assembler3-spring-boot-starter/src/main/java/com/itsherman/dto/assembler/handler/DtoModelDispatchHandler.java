package com.itsherman.dto.assembler.handler;

import com.itsherman.dto.assembler.core.ModelDefinition;
import com.itsherman.dto.assembler.core.ValidMessage;
import com.itsherman.dto.assembler.factory.ClassDtoPrepareFactory;
import com.itsherman.dto.assembler.factory.DtoPrepareFactory;
import com.itsherman.dto.assembler.factory.InterfaceDtoPrepareFactory;
import com.itsherman.dto.assembler.utils.SpringUtils;
import com.itsherman.dto.assembler.validator.AbstractDtoValidator;

import java.util.List;

public class DtoModelDispatchHandler {

    public void handle(List<Class<?>> dtoClasses) {
        for (Class<?> dtoClass : dtoClasses) {
            DtoPrepareFactory dtoPrerareFactory;
            if (dtoClass.isInterface()) {
                dtoPrerareFactory = SpringUtils.getBean("interfaceDtoPrepareManager", InterfaceDtoPrepareFactory.class);
            } else {
                dtoPrerareFactory = SpringUtils.getBean("classDtoPrepareManager", ClassDtoPrepareFactory.class);
            }
            ModelDefinition md = dtoPrerareFactory.getDtoModelRegister().register(dtoClass);
            AbstractDtoValidator validator = dtoPrerareFactory.getDtoValiddator();
            validator.setMd(md);
            List<ValidMessage> validMessages = validator.validate();
            if (validMessages.isEmpty()) {

            }
        }
    }
}
