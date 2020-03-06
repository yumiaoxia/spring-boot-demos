package com.itsherman.dto.assembler.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class ValidatorHolder {

    public static List<DtoValidator> dtoValidators = new ArrayList<>();

    static {
        dtoValidators.add(new FieldExistValidator());
        dtoValidators.add(new FieldSourceValidator());
    }
}
