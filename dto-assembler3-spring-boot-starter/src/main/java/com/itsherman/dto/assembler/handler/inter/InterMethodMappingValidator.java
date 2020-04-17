package com.itsherman.dto.assembler.handler.inter;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.annotations.DtoProperty;
import com.itsherman.dto.assembler.core.InterfacePropertyDefinition;
import com.itsherman.dto.assembler.core.ModelDefinition;
import com.itsherman.dto.assembler.core.ModelPropertyDefinition;
import com.itsherman.dto.assembler.core.ValidMessage;
import com.itsherman.dto.assembler.validator.AbstractDtoValidator;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InterMethodMappingValidator extends AbstractDtoValidator {

    @Override
    public List<ValidMessage> validate() {
        ModelDefinition md = getMd();
        Class<?> dtoClass = md.getDtoClass();
        List<ValidMessage> validMessages = new ArrayList<>();
        if (dtoClass.isInterface()) {
            List<ModelPropertyDefinition> mpds = md.getModelProperties();
            for (ModelPropertyDefinition mpd : mpds) {
                InterfacePropertyDefinition ipd = (InterfacePropertyDefinition) mpd;
                Method dtoMethod = ipd.getDtoMethod();
                DtoProperty dtoProperty = dtoMethod.getAnnotation(DtoProperty.class);
                DtoModel dtoModel = dtoClass.getAnnotation(DtoModel.class);
                List<Class<?>> fromClasses = md.getFromClasses();
                String methodName = dtoMethod.getName();
                if (dtoProperty == null) {
                    validMessages.addAll(loopValid(ipd, fromClasses));
                } else {
                    if (dtoProperty.sourceClass().equals(Void.class)) {
                        validMessages.addAll(loopValid(ipd, fromClasses));
                    } else if (fromClasses.contains(dtoProperty.sourceClass())) {
                        mpd.setSourceClass(dtoProperty.sourceClass());
                    } else {
                        ValidMessage message = new ValidMessage();
                        message.setMessage(String.format("%s : Annotation DtoProperty with incorrect sourceClass on Field %s,it's sourceClass must be one of fromClass", dtoClass.getSimpleName(), methodName));
                        validMessages.add(message);
                    }
                }
            }
        }
        return validMessages;
    }


    public List<ValidMessage> loopValid(InterfacePropertyDefinition ipd, List<Class<?>> fromClasses) {
        List<ValidMessage> validMessages = new ArrayList<>();
        String methodName = ipd.getDtoMethod().getName();
        boolean flag = false;
        for (Class fromClass : fromClasses) {
            Optional<Method> methodOp = Arrays.stream(fromClass.getMethods())
                    .filter(method -> method.getName().equals(methodName) && !Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers()))
                    .findFirst();
            if (methodOp.isPresent()) {
                flag = true;
                ipd.setSourceClass(fromClass);
                break;
            }
        }
        if (!flag) {
            ValidMessage message = new ValidMessage();
            message.setMessage(String.format("%s : cant not found readMethod —— %s in fromClasses", ipd.getMd().getDtoClass().getSimpleName(), methodName));
            validMessages.add(message);
        }
        return validMessages;
    }
}
