package com.itsherman.commondto2.manage;

import com.itsherman.commondto2.core.DtoDefinition;
import com.itsherman.commondto2.core.DtoDefinitionHolder;
import com.itsherman.commondto2.core.DtoPropertyDefinition;
import com.itsherman.commondto2.example.dto.dto2.BookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
            if (Modifier.isFinal(fieldClazz.getModifiers()) || fieldClazz.isPrimitive()) {
                if (fieldClazz.isArray()) {
                    Class valueClazz = fieldClazz.getComponentType();
                    if (!Modifier.isFinal(valueClazz.getModifiers())) {
                        Object[] values = (Object[]) value;
                        BookDto[] dtoValues = new BookDto[values.length];
                        for (int i = 0; i < values.length; i++) {
                            dtoValues[i] = ((Optional<BookDto>) DtoTransFormer.as(valueClazz).apply(values[i])).get();
                        }
                        value = dtoValues;
                    }
                }
            } else if (Collection.class.isAssignableFrom(fieldClazz)) {
                Collection sourceCollection = (Collection) value;
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                Class actualType = (Class) pt.getActualTypeArguments()[0];
                value = DtoTransFormer.asList(actualType).apply(sourceCollection);
            } else {
                value = ((Optional) DtoTransFormer.as(fieldClazz).apply(value)).get();
            }
            writeMethod.invoke(dest, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
