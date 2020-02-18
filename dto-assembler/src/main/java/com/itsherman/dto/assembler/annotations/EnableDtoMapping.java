package com.itsherman.dto.assembler.annotations;

import com.itsherman.dto.assembler.config.DtoMappingScannerRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DtoMappingScannerRegister.class)
public @interface EnableDtoMapping {


    DtoMapping[] mappings() default {};
}
