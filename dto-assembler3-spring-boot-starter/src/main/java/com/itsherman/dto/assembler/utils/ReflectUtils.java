package com.itsherman.dto.assembler.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectUtils {

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Collections.addAll(fields, clazz.getDeclaredFields());
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && !Object.class.equals(superClass)) {
            fields.addAll(getAllFields(superClass));
        }
        return fields;
    }

    public static List<Field> getAllNonStaticFields(Class<?> clazz) {
        List<Field> fields = getAllFields(clazz);
        return fields.stream().filter(field -> !Modifier.isStatic(field.getModifiers())).collect(Collectors.toList())
                ;
    }
}
