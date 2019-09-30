package com.itsherman.commondto2.manage;

import com.itsherman.commondto2.core.DtoDefinition;
import com.itsherman.commondto2.core.DtoDefinitionHolder;
import com.itsherman.commondto2.core.DtoPropertyDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
public class DtoAssembler {

    private static final Logger log = LoggerFactory.getLogger(DtoAssembler.class);

    public static Object assemble(Class<?> dtoClass, Object source) {
        Object object = null;
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
                Method readMethod = dtoPropertyDefinition.getReadMethod();
                Method writeMethod = dtoPropertyDefinition.getWriteMethod();
                try {
                    Object value = readMethod.invoke(source);
                    writeMethod.invoke(object, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return object;
    }
}
