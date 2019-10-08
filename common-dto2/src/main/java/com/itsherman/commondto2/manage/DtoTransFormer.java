package com.itsherman.commondto2.manage;

import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoTransFormer {


    public static <T, R> Function<T, Optional<R>> as(Class<R> dtoClass) {
        return t -> Optional.ofNullable(t).map(o -> DtoAssembler.assemble(dtoClass, o));
    }

    public static <R> ListHelper<R> asList(Class<R> dtoClass) {
        return new ListHelper<>(dtoClass);
    }

    public static <R> PageHelper<R> asPagest(Class<R> dtoClass) {
        return new PageHelper<>(dtoClass);
    }

    public static class ListHelper<R> {

        private Class<R> dtoClass;

        public ListHelper(Class<R> dtoClass) {
            this.dtoClass = dtoClass;
        }

        public <T> List<R> apply(Collection<T> collection) {
            return collection.stream().map(t -> DtoAssembler.assemble(dtoClass, t)).collect(Collectors.toList());
        }
    }

    private static class PageHelper<R> {
        private Class<R> dtoClass;

        public PageHelper(Class<R> dtoClass) {
            this.dtoClass = dtoClass;
        }

        public <T> Page<R> apply(Page<T> page) {
            return page.map(o -> DtoAssembler.assemble(dtoClass, o));
        }
    }


}
