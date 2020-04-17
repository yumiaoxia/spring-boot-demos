package com.itsherman.dto.assembler.handler;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.constants.Commonconstants;
import com.itsherman.dto.assembler.core.*;
import com.itsherman.dto.assembler.handler.clazz.ClassFieldMappingValidator;
import com.itsherman.dto.assembler.handler.inter.InterMethodMappingValidator;
import com.itsherman.dto.assembler.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DtoPreparedHandler {

    private static final Logger log = LoggerFactory.getLogger(DtoPreparedHandler.class);

    @Resource
    private ClassFieldMappingValidator classFieldMappingValidator;

    @Resource
    private InterMethodMappingValidator interfaceMethodMappingValidator;

    @Resource
    private DtoViewRegisHandler dtoViewRegisHandler;

    public void handle(Collection<Class<?>> classes) {
        if (!classes.isEmpty()) {
            Iterator<Class<?>> it = classes.iterator();
            int modelId = 1;
            List<ValidMessage> validMessages = null;
            while (it.hasNext()) {
                Class<?> clazz = it.next();
                ModelDefinition md = new ModelDefinition();
                md.setId(modelId);
                md.setDtoClass(clazz);
                DtoModel dtoModel = clazz.getAnnotation(DtoModel.class);
                List<Class<?>> fromClasses = new ArrayList<>();
                Collections.addAll(fromClasses, dtoModel.from());
                md.setFromClasses(fromClasses);
                if (clazz.isInterface()) {
                    Method[] dtoMethods = clazz.getMethods();
                    List<ModelPropertyDefinition> ipds = Arrays.stream(dtoMethods).filter(dtoMethod ->
                            dtoMethod.getName().startsWith(Commonconstants.GETTER_PREFIX) && !Modifier.isStatic(dtoMethod.getModifiers()))
                            .map(method -> {
                                InterfacePropertyDefinition ipd = new InterfacePropertyDefinition();
                                ipd.setDtoMethod(method);
                                return ipd;
                            }).collect(Collectors.toList());
                    md.setModelProperties(ipds);
                    interfaceMethodMappingValidator.setMd(md);
                    validMessages = interfaceMethodMappingValidator.validate();

                } else {
                    List<Field> fields = ReflectUtils.getAllNonStaticFields(clazz);
                    List<ModelPropertyDefinition> mpds = fields.stream()
                            .map(field -> {
                                ClassPropertyDefinition cpd = new ClassPropertyDefinition();
                                cpd.setField(field);
                                return cpd;
                            })
                            .collect(Collectors.toList());
                    md.setModelProperties(mpds);
                    classFieldMappingValidator.setMd(md);
                    validMessages = classFieldMappingValidator.validate();
                }
                if (validMessages.isEmpty()) {
                    for (ValidMessage validMessage : validMessages) {
                        log.error(validMessage.getMessage());
                    }
                } else {
                    dtoViewRegisHandler.handle(md);
                }
            }
        }
    }
}
