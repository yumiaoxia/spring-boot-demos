package com.itsherman.dto.assembler.validator;

import com.itsherman.dto.assembler.annotations.DtoProperty;
import com.itsherman.dto.assembler.constants.Commonconstants;
import com.itsherman.dto.assembler.core.*;

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
 * @since 2019-09-30
 */
public class FieldExistValidator implements DtoValidator {

    @Override
    public Set<ValidMessage> validate(DtoDefinition dtoDefinition, Class<?> dtoClass, Class[] fromClasses) {
        Set<ValidMessage> validMessages = new HashSet<>();
        if (dtoClass.getSuperclass() != null) {
            Set<Field> fieldSet = Arrays.stream(dtoClass.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .collect(Collectors.toSet());

            for (Field field : fieldSet) {
                ClassDtoPropertyDefinition dpd = new ClassDtoPropertyDefinition(dtoDefinition);
                dpd.setField(field);

                String methodSuffix = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method writeMethod;
                try {
                    writeMethod = dtoClass.getDeclaredMethod(Commonconstants.GETTER_PREFIX + methodSuffix);
                    dpd.setWriteMethod(writeMethod);
                } catch (NoSuchMethodException ex) {
                    validMessages.add(new ValidMessage(false, String.format("Field %s can not found writeMethod", field.getName())));
                    continue;
                }
                DtoProperty dtoProperty = Optional.ofNullable(field.getAnnotation(DtoProperty.class)).orElse(writeMethod.getAnnotation(DtoProperty.class));
                boolean flag;
                if (dtoProperty != null) {
                    String value = dtoProperty.value().equals("") ? field.getName() : dtoProperty.value();
                    String methodName = Commonconstants.GETTER_PREFIX + value.substring(0, 1).toUpperCase() + value.substring(1);
                    if (dtoProperty.sourceClass().equals(Void.class)) {
                        flag = checkReadMethodInFormClass(fromClasses, methodName, dpd);
                    } else {
                        flag = checkContainReadMethod(dtoProperty.sourceClass(), methodName, dpd);
                    }
                } else {
                    String methodName = Commonconstants.GETTER_PREFIX + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    flag = checkReadMethodInFormClass(fromClasses, methodName, dpd);
                }
                if (!flag) {
                    validMessages.add(new ValidMessage(false, String.format("Field %s can not found readMethod", field.getName())));
                }
                dtoDefinition.getValidPropertyDefinitions().add(dpd);
            }
        } else if (dtoClass.isInterface()) {
            Set<Method> methods = Arrays.stream(dtoClass.getDeclaredMethods())
                    .filter(method -> method.getName().startsWith(Commonconstants.GETTER_PREFIX))
                    .collect(Collectors.toSet());
            for (Method method : methods) {
                if (!method.isDefault()) {
                    DtoPropertyDefinition dpd = new InterfaceDtoPropertyDefinition(dtoDefinition, method);
                    DtoProperty dtoProperty = method.getAnnotation(DtoProperty.class);
                    boolean flag;
                    if (dtoProperty != null) {
                        if (dtoProperty.sourceClass().equals(Void.class)) {
                            flag = checkReadMethodInFormClass(fromClasses, method.getName(), dpd);
                        } else {
                            flag = checkContainReadMethod(dtoProperty.sourceClass(), method.getName(), dpd);
                        }
                    } else {
                        flag = checkReadMethodInFormClass(fromClasses, method.getName(), dpd);
                    }
                    if (!flag) {
                        validMessages.add(new ValidMessage(false, String.format("DtoClass %s can not found readMethod %s in sourceClass", dtoDefinition.getDtoClass().getName(), method.getName())));
                    }
                    dtoDefinition.getValidPropertyDefinitions().add(dpd);
                } else {
                    dtoDefinition.getValidPropertyDefinitions().add(new InterfaceDtoPropertyDefinition(dtoDefinition, method));
                }
            }
        }
        return validMessages;
    }

    private boolean checkContainReadMethod(Class sourceClass, String methodName, DtoPropertyDefinition dpd) {
        boolean flag = false;
        try {
            Method method = sourceClass.getDeclaredMethod(methodName);
            if (method != null && Modifier.isPublic(method.getModifiers())) {
                dpd.setReadMethod(method);
                dpd.setSourceClass(sourceClass);
                return true;
            }
        } catch (NoSuchMethodException ignored) {
        }
        return flag;
    }

    private boolean checkReadMethodInFormClass(Class[] fromClasses, String methodName, DtoPropertyDefinition dpd) {
        boolean flag = false;
        for (Class fromClass : fromClasses) {
            if (checkContainReadMethod(fromClass, methodName, dpd)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
