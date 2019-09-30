package com.itsherman.commondto2.validator;

import com.itsherman.commondto2.annotations.DtoProperty;
import com.itsherman.commondto2.core.DtoDefinition;
import com.itsherman.commondto2.core.ValidMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
public class FieldSourceValidator implements DtoValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldSourceValidator.class);

    @Override
    public Set<ValidMessage> validate(DtoDefinition dtoDefinition, Class<?> dtoClass, Class[] fromClasses) {
        Set<ValidMessage> validMessages = new HashSet<>();
        Field[] fields = dtoClass.getFields();
        for (Field field : fields) {
            DtoProperty annotation = field.getAnnotation(DtoProperty.class);
            if (annotation != null) {
                Class sourceClazz = annotation.sourceClass();
                if (sourceClazz != Void.class) {
                    boolean flag = false;
                    for (Class fromClass : fromClasses) {
                        if (fromClass.equals(sourceClazz)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        validMessages.add(new ValidMessage(false, String.format("Annotation named 'DtoProperty' on Field %s has error property sourceClass,it can not suit the property 'fromClasses' of annotation DtoMapping onType", field.getName())));
                    }
                }
            }
        }
        return validMessages;
    }
}
