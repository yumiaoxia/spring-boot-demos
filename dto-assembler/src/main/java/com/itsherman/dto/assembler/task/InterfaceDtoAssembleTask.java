package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.constants.Commonconstants;
import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.DtoDefinitionHolder;
import com.itsherman.dto.assembler.core.DtoPropertyDefinition;
import com.itsherman.dto.assembler.core.InterfaceDtoPropertyDefinition;
import com.itsherman.dto.assembler.exception.DtoAssembleException;
import com.itsherman.dto.assembler.exception.TypeCastException;
import com.itsherman.dto.assembler.utils.DtoAssembleUtils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.*;

public class InterfaceDtoAssembleTask<T, R> implements DtoAssembleTask<T, R> {


    @Override
    public Optional<R> assemble(DtoDefinition dtoDefinition, T... ts) {
        Class<?> dtoClass = dtoDefinition.getDtoClass();
        return (Optional<R>) Optional.of(Proxy.newProxyInstance(dtoClass.getClassLoader(), new Class[]{dtoClass}, (proxy, method, args) -> {
            Object result = null;
            if (method.getName().startsWith(Commonconstants.GETTER_PREFIX)) {
                Set<DtoPropertyDefinition> validPropertyDefinitions = dtoDefinition.getValidPropertyDefinition();
                for (DtoPropertyDefinition validPropertyDefinition : validPropertyDefinitions) {
                    InterfaceDtoPropertyDefinition propertyDefinition = (InterfaceDtoPropertyDefinition) validPropertyDefinition;
                    if (propertyDefinition.getDtoMethod().equals(method)) {
                        if (method.isDefault()) {
                            Class<?> declaringClass = method.getDeclaringClass();
                            Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
                            constructor.setAccessible(true);
                            result = constructor
                                    .newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
                                    .unreflectSpecial(method, declaringClass)
                                    .bindTo(proxy)
                                    .invokeWithArguments(args);
                        } else {
                            Optional<T> sourceOptional = Arrays.stream(ts).filter(t -> t.getClass().isAssignableFrom(propertyDefinition.getSourceClass())).findFirst();
                            Method readMethod = propertyDefinition.getReadMethod();
                            Object readMethodValue;
                            if (sourceOptional.isPresent()) {
                                readMethodValue = readMethod.invoke(sourceOptional.get(), args);
                            } else {
                                throw new DtoAssembleException(String.format("Illegal Source Object,it required sourceClass '%s',but not found", propertyDefinition.getSourceClass()));
                            }
                            Class<?> returnType = propertyDefinition.getDtoMethod().getReturnType();
                            try {
                                result = doAssemble(returnType, propertyDefinition.getReadMethod().getReturnType(), readMethodValue);
                            } catch (RuntimeException e) {
                                throw new DtoAssembleException(String.format("无法编集 %s 到 %s。 目标类型：%s, 原因：%s", readMethod.getName(), propertyDefinition.getDtoMethod().getName(), dtoClass.getSimpleName(), e.getMessage()), e);
                            }
                        }
                    }
                }
            }
            if (result == null) {
                if (method.getReturnType().isAssignableFrom(Void.class)) {
                    return Void.class.newInstance();
                }
            }
            return result;
        }));
    }


    private Object doAssemble(Class<?> dtoReturnType, Class<?> sourceReturnType, Object sourceReturnValue) {
        // 判断返回类型
        if (sourceReturnType.equals(dtoReturnType) && Modifier.isFinal(dtoReturnType.getModifiers())) {
            return sourceReturnValue;
        } else if (dtoReturnType.isPrimitive() && sourceReturnType.equals(dtoReturnType)) {
            return sourceReturnValue;
        } else if (DtoDefinitionHolder.getDtoDefinitions().get(dtoReturnType) != null) {
            return DtoAssembleUtils.assemble(dtoReturnType, sourceReturnValue);
        } else if (dtoReturnType.isArray()) {
            Object[] arrayValues = (Object[]) sourceReturnValue;
            Class<?> componentType = dtoReturnType.getComponentType();
            Object[] dtoValues = new Object[arrayValues.length];
            for (int i = 0; i < arrayValues.length; i++) {
                dtoValues[i] = doAssemble(componentType, sourceReturnType.getComponentType(), arrayValues[i]);
            }
            return dtoValues;
        } else if (dtoReturnType.isAssignableFrom(Collection.class)) {
            Collection<Object> collectionValues = (Collection<Object>) sourceReturnValue;
            Collection<Object> dtoValues = Collections.emptyList();
            for (Object collectionValue : collectionValues) {
                System.out.println(dtoReturnType.getTypeParameters()[0].getGenericDeclaration());
                dtoValues.add(doAssemble(dtoReturnType.getTypeParameters()[0].getGenericDeclaration(), collectionValue.getClass(), collectionValue));
            }
            return dtoValues;
        } else {
            throw new TypeCastException(String.format("Type match Error,target type '%s',source type '%s'", dtoReturnType, sourceReturnType));
        }
    }
}
