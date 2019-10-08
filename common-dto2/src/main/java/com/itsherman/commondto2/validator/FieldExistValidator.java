package com.itsherman.commondto2.validator;

import com.itsherman.commondto2.annotations.DtoProperty;
import com.itsherman.commondto2.core.DtoDefinition;
import com.itsherman.commondto2.core.DtoPropertyDefinition;
import com.itsherman.commondto2.core.ValidMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class FieldExistValidator implements DtoValidator {
    private static final String GET = "get";
    private static final String SET = "set";

    @Override
    public Set<ValidMessage> validate(DtoDefinition dtoDefinition,Class<?> dtoClass, Class[] fromClasses) {
        Set<ValidMessage> validMessages = new HashSet<>();
        Field[] fields = dtoClass.getDeclaredFields();
        Method[] methods = dtoClass.getMethods();
        boolean hasGetter = false;
        for (Field field : fields) {
            Class<?> fieldClass = field.getType();
                DtoPropertyDefinition dpd = new DtoPropertyDefinition();
                dpd.setField(field);
                String methodSuffix = field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
                for (Method method : methods) {
                    if(method.getName().equals(GET + methodSuffix)){
                        hasGetter = true;
                        if(!Modifier.isPublic(method.getModifiers())){
                            validMessages.add(new ValidMessage(false,String.format("Field %s has getter method,but is not public",field.getName())));
                        }
                        DtoProperty dtoProperty = Optional.ofNullable(field.getAnnotation(DtoProperty.class)).orElse(method.getAnnotation(DtoProperty.class));
                        if(dtoProperty != null){
                            String value = dtoProperty.value().equals("") ? field.getName() : dtoProperty.value();
                            boolean temFlag = false;
                            if(dtoProperty.sourceClass().equals(Void.class)){
                                for (Class fromClass : fromClasses) {
                                    Method[] fromMethods = fromClass.getMethods();
                                    for (Method fromMethod : fromMethods) {
                                        if(fromMethod.getName().equals(GET + value.substring(0,1).toUpperCase() + value.substring(1)) && Modifier.isPublic(fromMethod.getModifiers())){
                                            dpd.setReadMethod(fromMethod);
                                            temFlag = true;
                                            break;
                                        }
                                    }
                                    if(temFlag){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(method.getName().equals(SET + methodSuffix) && Modifier.isPublic(method.getModifiers())){
                        dpd.setWriteMethod(method);
                    }
                }
                if(!hasGetter){
                    validMessages.add(new ValidMessage(false,String.format("Field %s can not found getter method",field.getName())));
                }
                if(dpd.getReadMethod() == null){
                    validMessages.add(new ValidMessage(false,String.format("Field %s can not found readMethod",field.getName())));
                }
                if(dpd.getWriteMethod() == null){
                    validMessages.add(new ValidMessage(false,String.format("Field %s can not found writeMethod",field.getName())));
                }
                if(validMessages.size() == 0){
                    dtoDefinition.getValidPropertyDefinition().add(dpd);
                }
        }
        return validMessages;
    }
}
