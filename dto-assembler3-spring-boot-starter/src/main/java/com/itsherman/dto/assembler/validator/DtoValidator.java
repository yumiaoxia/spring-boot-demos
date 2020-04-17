package com.itsherman.dto.assembler.validator;

import com.itsherman.dto.assembler.core.ValidMessage;

import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
public interface DtoValidator {

    List<ValidMessage> validate();
}
