package com.itsherman.dto.assembler.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoTransFormer {

    public static <R> Helper<R> to(Class<R> dtoClass) {
        return new Helper<>(dtoClass);
    }

    public static <R> ListHelper<R> toList(Class<R> dtoClass) {
        return new ListHelper<>(dtoClass);
    }


    public static class Helper<R> {

        private final Class<R> dtoClass;

        public Helper(Class<R> dtoClass) {
            this.dtoClass = dtoClass;
        }

        @SafeVarargs
        public final <T> R apply(T... ts) {
            return DtoAssembleUtils.assemble(dtoClass, ts);
        }
    }

    public static class ListHelper<R> {
        private final Class<R> dtoClass;

        public ListHelper(Class<R> dtoClass) {
            this.dtoClass = dtoClass;
        }

        public <T> List<R> apply(Collection<T> ts) {
            return ts.stream().map(t -> DtoAssembleUtils.assemble(dtoClass, t)).collect(Collectors.toList());
        }
    }


}
