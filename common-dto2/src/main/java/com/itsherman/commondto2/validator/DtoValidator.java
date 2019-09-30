package com.itsherman.commondto2.validator;

import com.itsherman.commondto2.core.DtoDefinition;
import com.itsherman.commondto2.core.ValidMessage;

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
