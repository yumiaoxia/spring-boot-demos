package com.itsherman.commondto2.annotations;

import java.lang.annotation.*;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-09
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DtoMapping {

    String basePackage() default "";

    boolean isRecursion() default true;
}
