package com.itsherman.common.dto.util;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-09
 */
public class ArraysUtil {

    public static boolean contains(Object[] array, Object item) {
        boolean flag = false;
        if (array != null && item != null) {
            for (Object o : array) {
                if (o.equals(item)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public static boolean contains(Object[] array, Class clazz) {
        boolean flag = false;
        if (array != null && clazz != null) {
            for (Object o : array) {
                if (clazz.isInstance(o)) {
                    flag = true;
                }
            }
        }
        return flag;
    }
}
