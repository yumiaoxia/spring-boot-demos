package com.itsherman.dto.assembler;

import com.itsherman.dto.assembler.utils.DtoAssembleUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoTransFormer {

    public static <T, R> Function<T[], R> to(Class<R> dtoClass) {
        return ts -> DtoAssembleUtils.assemble(dtoClass, ts);
    }

    public static <T, R> Function<List<T>, List<R>> toList(Class<R> dtoClass) {
        return ts -> ts.stream().map(t -> DtoAssembleUtils.assemble(dtoClass, t)).collect(Collectors.toList());
    }

    public static <T, R> Function<Page<T>, Page<R>> toPage(Class<R> dtoClass) {
        return ts -> {
            List<T> contents = ts.getContent();
            List<R> resultContents = contents.stream().map(content -> DtoAssembleUtils.assemble(dtoClass, content)).collect(Collectors.toList());
            return new PageImpl<>(resultContents, ts.getPageable(), ts.getTotalElements());
        };
    }


}
