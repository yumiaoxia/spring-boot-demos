package com.itsherman.dto.assembler;

import com.itsherman.dto.assembler.task.ClassDtoAssembleTask;
import com.itsherman.dto.assembler.task.DtoAssembleTask;

import java.util.function.Function;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoTransFormer {

    public static <T,R> Function to(Class<R> dtoClass){

        return  new Function<T[],R>() {
            @Override
            public R apply(T... t) {
                DtoAssembleTask<T,R> dtoAssembleTask = new ClassDtoAssembleTask<>();
                return dtoAssembleTask.assemble(dtoClass,t);
            }
        };
    }

    private static class ToBuilder{

    }

}
