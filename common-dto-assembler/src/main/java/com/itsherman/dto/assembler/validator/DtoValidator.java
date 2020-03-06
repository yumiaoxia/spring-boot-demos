package com.itsherman.dto.assembler.validator;

import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.ValidMessage;

import java.util.Set;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
public interface DtoValidator {

    Set<ValidMessage> validate(DtoDefinition dtoDefinition, Class<?> dtoClass, Class[] fromClasses);
}
