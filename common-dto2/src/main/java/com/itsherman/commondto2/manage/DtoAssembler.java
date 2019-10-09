package com.itsherman.commondto2.manage;

import com.itsherman.commondto2.core.DtoDefinition;
import com.itsherman.commondto2.core.DtoDefinitionHolder;
import com.itsherman.commondto2.core.DtoPropertyDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
public class DtoAssembler {

    private static final Logger log = LoggerFactory.getLogger(DtoAssembler.class);

    public static <T, R> R assemble(Class<R> dtoClass, T source) {
        R object = null;
        try {
            object = dtoClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Map<Class, DtoDefinition> dtoDefinitions = DtoDefinitionHolder.getDtoDefinitions();
        DtoDefinition dtoDefinition = dtoDefinitions.get(dtoClass);
        Class<?>[] fromClass = dtoDefinition.getFromClass();
        boolean temFlag = false;
        for (Class<?> aClass : fromClass) {
            if (aClass.isInstance(source)) {
                temFlag = true;
                break;
            }
        }
        if (!temFlag) {
            log.error("from unavailable,{} instance of {} == false", source, fromClass);
        } else {
            Set<DtoPropertyDefinition> propertyDefinitions = dtoDefinition.getValidPropertyDefinition();
            for (DtoPropertyDefinition dtoPropertyDefinition : propertyDefinitions) {
                propertyAssemble(dtoPropertyDefinition, source, object, dtoDefinitions);
            }
        }
        return object;
    }

    private static void propertyAssemble(DtoPropertyDefinition dtoPropertyDefinition, Object source, Object dest, Map<Class, DtoDefinition> dtoDefinitions) {
        Field field = dtoPropertyDefinition.getField();
        Class fieldClazz = field.getType();
        Method readMethod = dtoPropertyDefinition.getReadMethod();
        Method writeMethod = dtoPropertyDefinition.getWriteMethod();
        Object value = null;
        try {
            value = readMethod.invoke(source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (Modifier.isFinal(fieldClazz.getModifiers()) || fieldClazz.isPrimitive()) {
            if (fieldClazz.isArray()) {
                Object[] values = (Object[]) value;
                Class fieldGenericType = (Class) field.getGenericType();
                Class clazz = fieldGenericType.getComponentType();
                Object[] dtoValues = new Object[values.length];
                int record = 0;
                for (Object o : values) {
                    dtoValues[record++] = ((Optional) DtoTransFormer.as(clazz).apply(o)).get();
                }
                try {
                    writeMethod.invoke(dest, dtoValues);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    writeMethod.invoke(dest, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } else if (Collection.class.isAssignableFrom(fieldClazz)) {
            Collection sourceCollection = (Collection) value;
            ParameterizedType pt = (ParameterizedType) field.getGenericType();
            Class actualType = (Class) pt.getActualTypeArguments()[0];
            List<?> destCollection = DtoTransFormer.asList(actualType).apply(sourceCollection);
            try {
                writeMethod.invoke(dest, destCollection);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else if (fieldClazz.isArray()) {
            Type type = field.getGenericType();
            String t = null;
        }
    }
}
