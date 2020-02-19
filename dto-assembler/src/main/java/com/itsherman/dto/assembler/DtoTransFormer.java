package com.itsherman.dto.assembler;

import com.itsherman.dto.assembler.utils.DtoAssembleUtils;

import java.util.Optional;
import java.util.function.Function;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoTransFormer {

    public static <T, R> Function<T[], Optional<R>> to(Class<R> dtoClass) {
        return ts -> {
            return DtoAssembleUtils.assemble(dtoClass, ts);
        };
    }


}
