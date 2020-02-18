package com.itsherman.dto.assembler.annotations;

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

    /**
     * DTO的包路径
     *
     * @return
     */
    String basePackage() default "";

    /**
     * 是否扫描子包
     *
     * @return
     */
    boolean isRecursion() default true;
}
