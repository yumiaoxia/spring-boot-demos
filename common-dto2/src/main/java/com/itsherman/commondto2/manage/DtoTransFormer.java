package com.itsherman.commondto2.manage;

import java.util.function.Function;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoTransFormer {


    private static class ObjectHelper<T, R> {
        private Class<T> dtoClass;

        public ObjectHelper(Class<T> dtoClass) {
            this.dtoClass = dtoClass;
        }

        public R apply(Object source) {
            return new Function<Class<T>, R>(dtoClass -> DtoAssembler.assemble(dtoClass, source));
        }
    }


}
