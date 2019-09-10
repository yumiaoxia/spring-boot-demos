package com.itsherman.common.dto.annotations;

import java.lang.annotation.*;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-09
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DtoProperty {

    /**
     * 参照源属性名
     */
    String value() default "";

    /**
     * 参照源
     */
    Class sourceClass() default Void.class;


}
