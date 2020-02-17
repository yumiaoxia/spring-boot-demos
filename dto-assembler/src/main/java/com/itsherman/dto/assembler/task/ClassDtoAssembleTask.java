package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.annotations.DtoModel;

public class ClassDtoAssembleTask<T,R> implements DtoAssembleTask<T,R>{

    @Override
    public R assemble(Class<R> rClass, T... ts) {
        DtoModel[] annotations = rClass.getAnnotationsByType(DtoModel.class);
        if(annotations != null){
            for (DtoModel annotation : annotations) {
                Class[] froms = annotation.from();
                for (T t : ts) {
                    for (Class from : froms) {
                        if(ts.getClass().isAssignableFrom(from)){

                        }
                    }
                }
            }
        }
        return null;
    }
}
