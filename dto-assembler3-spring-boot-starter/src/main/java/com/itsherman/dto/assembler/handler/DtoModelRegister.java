package com.itsherman.dto.assembler.handler;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.core.ModelDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DtoModelRegister {

    public ModelDefinition register(Class<?> dtoClass) {
        ModelDefinition md = new ModelDefinition();
        md.setDtoClass(dtoClass);
        DtoModel dtoModel = dtoClass.getAnnotation(DtoModel.class);
        List<Class<?>> fromClasses = new ArrayList<>();
        Collections.addAll(fromClasses, dtoModel.from());
        md.setFromClasses(fromClasses);
        doRegister(md);
        return md;
    }


    public abstract void doRegister(ModelDefinition md);
}
