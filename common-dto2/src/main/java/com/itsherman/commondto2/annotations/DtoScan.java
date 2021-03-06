package com.itsherman.commondto2.annotations;

import java.lang.annotation.*;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-18
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DtoScan {

    String[] basePackages() default "";
}
