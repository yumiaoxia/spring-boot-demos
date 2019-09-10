package com.itsherman.common.dto.annotations;

import java.lang.annotation.*;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-09
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DtoMapping {

    /**
     * 参照源
     */
    Class[] from() default Void.class;
}
