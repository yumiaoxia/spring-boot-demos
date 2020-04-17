package com.itsherman.dto.assembler.handler.clazz;

import com.itsherman.dto.assembler.annotations.DtoProperty;
import com.itsherman.dto.assembler.constants.Commonconstants;
import com.itsherman.dto.assembler.core.ClassPropertyDefinition;
import com.itsherman.dto.assembler.core.ModelPropertyDefinition;
import com.itsherman.dto.assembler.core.ValidMessage;
import com.itsherman.dto.assembler.validator.AbstractDtoValidator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ClassFieldMappingValidator extends AbstractDtoValidator {

    @Override
    public List<ValidMessage> validate() {
        Class<?> dtoClass = getMd().getDtoClass();
        List<ModelPropertyDefinition> modelPropertyDefinitions = getMd().getModelProperties();
        List<ValidMessage> validMessages = new ArrayList<>();
        for (ModelPropertyDefinition mpd : modelPropertyDefinitions) {
            ClassPropertyDefinition cpd = (ClassPropertyDefinition) mpd;
            Field field = cpd.getField();
            DtoProperty dtoProperty = field.getAnnotation(DtoProperty.class);
            List<Class<?>> fromClasses = getMd().getFromClasses();
            if (dtoProperty == null) {
                validMessages.addAll(loopValid(cpd, fromClasses));
            } else {
                Class sourceClass = dtoProperty.sourceClass();
                if (sourceClass.equals(Void.class)) {
                    validMessages.addAll(loopValid(cpd, fromClasses));
                } else {
                    if (fromClasses.contains(sourceClass)) {
                        mpd.setSourceClass(sourceClass);
                    } else {
                        ValidMessage message = new ValidMessage();
                        message.setSuccess(false);
                        message.setMessage(String.format("%s : Annotation DtoProperty with incorrect sourceClass on Field %s,it's sourceClass must be one of fromClass", dtoClass.getSimpleName(), field.getName()));
                        validMessages.add(message);
                    }
                }
            }
        }
        return validMessages;
    }

    public List<ValidMessage> loopValid(ClassPropertyDefinition cpd, List<Class<?>> fromClasses) {
        boolean flag = false;
        String readMethodName = Commonconstants.GETTER_PREFIX + cpd.getField().getName().substring(0, 1).toUpperCase() + cpd.getField().getName().substring(1);
        List<ValidMessage> validMessages = new ArrayList<>();
        for (Class<?> fromClass : fromClasses) {
            Method[] methods = fromClass.getMethods();
            Optional<Method> methodOp = Arrays.stream(methods).filter(method ->
                    method.getName().equals(readMethodName) && Modifier.isPublic(method.getModifiers()))
                    .findFirst();
            if (methodOp.isPresent()) {
                flag = true;
                cpd.setSourceClass(fromClass);
                break;
            }
        }
        if (!flag) {
            ValidMessage message = new ValidMessage();
            message.setSuccess(false);
            message.setMessage(String.format("%s ：cant not found readMethod —— %s in fromClasses", cpd.getMd().getDtoClass().getSimpleName(), readMethodName));
            validMessages.add(message);
        }
        return validMessages;
    }
}
