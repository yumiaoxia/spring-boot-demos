package com.itsherman.dto.assembler.handler.clazz;

import com.itsherman.dto.assembler.annotations.DtoProperty;
import com.itsherman.dto.assembler.constants.Commonconstants;
import com.itsherman.dto.assembler.core.ClassPropertyDefinition;
import com.itsherman.dto.assembler.core.ModelDefinition;
import com.itsherman.dto.assembler.core.ModelPropertyDefinition;
import com.itsherman.dto.assembler.exception.DtoPrepareException;
import com.itsherman.dto.assembler.handler.DtoModelRegister;
import com.itsherman.dto.assembler.utils.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassDtoModelRegister extends DtoModelRegister {

    @Override
    public void doRegister(ModelDefinition md) {
        Class<?> dtoClass = md.getDtoClass();
        List<Field> fields = ReflectUtils.getAllNonStaticFields(md.getDtoClass());
        List<Method> methods = Arrays.stream(dtoClass.getMethods())
                .filter(method -> method.getName().startsWith(Commonconstants.SETTER_PREFIX) && !Modifier.isStatic(method.getModifiers()))
                .collect(Collectors.toList());
        List<ModelPropertyDefinition> mpds = new ArrayList<>();
        for (Method method : methods) {
            ClassPropertyDefinition cpd = new ClassPropertyDefinition();
            cpd.setWriteMethod(method);
            String expectFieldName = method.getName().substring(2);
            expectFieldName = expectFieldName.substring(0, 1).toLowerCase() + expectFieldName.substring(1);
            String finalExpectFieldName = expectFieldName;
            DtoProperty dtoProperty = method.getAnnotation(DtoProperty.class);
            if (dtoProperty != null) {
                String value = dtoProperty.value();
                cpd.setValue(value.equals("") ? expectFieldName : value);
                Optional<Field> fieldOp = fields.stream().filter(field -> field.getName().equals(finalExpectFieldName)).findFirst();
                if (fieldOp.isPresent()) {
                    Field field = fieldOp.get();
                    cpd.setField(field);
                    dtoProperty = field.getAnnotation(DtoProperty.class);
                    if (dtoProperty != null) {
                        throw new DtoPrepareException(String.format("Duplicated annotation on field or method,fieldName: %s", field.getName()));
                    }
                }
            } else {
                Optional<Field> fieldOp = fields.stream().filter(field -> field.getName().equals(finalExpectFieldName)).findFirst();
                if (fieldOp.isPresent()) {
                    Field field = fieldOp.get();
                    cpd.setField(field);
                    dtoProperty = field.getAnnotation(DtoProperty.class);
                    if (dtoProperty != null) {
                        String value = dtoProperty.value();
                        cpd.setValue(value.equals("") ? expectFieldName : value);
                    } else {
                        cpd.setValue(finalExpectFieldName);
                    }

                }
            }
            mpds.add(cpd);
        }
        md.setModelProperties(mpds);
    }
}
