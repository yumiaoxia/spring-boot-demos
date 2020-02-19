package com.itsherman.dto.assembler.validator;

import com.itsherman.dto.assembler.annotations.DtoProperty;
import com.itsherman.dto.assembler.constants.Commonconstants;
import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.ValidMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
public class FieldSourceValidator implements DtoValidator {

    @Override
    public Set<ValidMessage> validate(DtoDefinition dtoDefinition, Class<?> dtoClass, Class[] fromClasses) {
        Set<ValidMessage> validMessages = new HashSet<>();
        if (dtoClass.getSuperclass() != null) {
            Field[] fields = dtoClass.getFields();
            Set<Field> fieldSet = Arrays.stream(fields)
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .collect(Collectors.toSet());
            for (Field field : fieldSet) {
                validMessages.addAll(collectValidMessages(field.getAnnotation(DtoProperty.class), fromClasses, field.getName(), dtoClass.getName()));
            }
            Method[] declaredMethods = dtoClass.getDeclaredMethods();
            Set<Method> readMethods = Arrays.stream(declaredMethods)
                    .filter(method -> method.getName().startsWith(Commonconstants.GETTER_PREFIX))
                    .collect(Collectors.toSet());
            for (Method readMethod : readMethods) {
                validMessages.addAll(collectValidMessages(readMethod.getAnnotation(DtoProperty.class), fromClasses, readMethod.getName(), dtoClass.getName()));
            }
        } else if (dtoClass.isInterface()) {
            Set<Method> readMethods = Arrays.stream(dtoClass.getDeclaredMethods())
                    .filter(method -> method.getName().startsWith(Commonconstants.GETTER_PREFIX) && Modifier.isPublic(method.getModifiers()))
                    .collect(Collectors.toSet());
            for (Method readMethod : readMethods) {
                validMessages.addAll(collectValidMessages(readMethod.getAnnotation(DtoProperty.class), fromClasses, readMethod.getName(), dtoClass.getName()));
            }
        }
        return validMessages;
    }

    private Set<ValidMessage> collectValidMessages(DtoProperty dtoProperty, Class[] fromClasses, String memberName, String className) {
        Set<ValidMessage> validMessages = new HashSet<>();
        if (dtoProperty != null) {
            Class sourceClass = dtoProperty.sourceClass();
            if (sourceClass != Void.class) {
                Optional<Class> flag = Arrays.stream(fromClasses)
                        .filter(fromClass -> fromClass.isAssignableFrom(sourceClass))
                        .findFirst();
                if (!flag.isPresent()) {
                    validMessages.add(new ValidMessage(false, String.format("Annotation named 'DtoProperty' on member %s of type %s has error property sourceClass,it can not suit the property 'fromClasses' of annotation 'DtoMapping' on Type", memberName, className)));
                }
            }
        }
        return validMessages;
    }
}
