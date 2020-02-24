package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.constants.Commonconstants;
import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.DtoDefinitionHolder;
import com.itsherman.dto.assembler.core.DtoPropertyDefinition;
import com.itsherman.dto.assembler.core.InterfaceDtoPropertyDefinition;
import com.itsherman.dto.assembler.exception.DtoAssembleException;
import com.itsherman.dto.assembler.exception.TypeCastException;
import com.itsherman.dto.assembler.manager.PropertyAssembleManager;
import com.itsherman.dto.assembler.utils.CollectionUtils;
import com.itsherman.dto.assembler.utils.DtoAssembleUtils;

import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class InterfaceDtoAssembler<T, R> implements DtoAssembleTask<T, R> {

    @Resource
    private PropertyAssembleManager propertyAssembleManager;

    @Override
    public Optional<R> assemble(DtoDefinition dtoDefinition, T... ts) {
        Class<?> dtoClass = dtoDefinition.getDtoClass();
        return (Optional<R>) Optional.of(Proxy.newProxyInstance(dtoClass.getClassLoader(), new Class[]{dtoClass}, (proxy, method, args) -> {
            Object result = null;
            if (method.getName().startsWith(Commonconstants.GETTER_PREFIX)) {
                Set<DtoPropertyDefinition> validPropertyDefinitions = dtoDefinition.getValidPropertyDefinitions();
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
                            if (readMethodValue != null) {
                                Type returnType = propertyDefinition.getDtoMethod().getGenericReturnType();
                                try {
                                    result = propertyAssembleManager.doAssemble(returnType, readMethodValue);
                                } catch (RuntimeException e) {
                                    throw new DtoAssembleException(String.format("无法编集 %s 到 %s。 目标类型：%s, 原因：%s", readMethod.getName(), propertyDefinition.getDtoMethod().getName(), dtoClass.getSimpleName(), e.getMessage()), e);
                                }
                            } else {
                                continue;
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


    private Object doAssemble(Type dtoReturnType, Object sourceReturnValue) {
        if (dtoReturnType instanceof Class) {
            Class dtoReturnClass = (Class) dtoReturnType;
            // 判断返回类型
            if (Modifier.isFinal(dtoReturnClass.getModifiers())) {
                return sourceReturnValue;
            } else if (dtoReturnClass.isPrimitive()) {
                return sourceReturnValue;
            } else if (DtoDefinitionHolder.getDtoDefinitions().get(dtoReturnType) != null) {
                return DtoAssembleUtils.assemble(dtoReturnClass, sourceReturnValue).orElse(null);
            } else if (dtoReturnClass.isArray()) {
                Object[] sourceArray = (Object[]) sourceReturnValue;
                Class<?> componentType = dtoReturnClass.getComponentType();
                Object dtoArray = Array.newInstance(componentType, sourceArray.length);
                for (int i = 0; i < sourceArray.length; i++) {
                    Object sourceValue = sourceArray[i];
                    Array.set(dtoArray, i, sourceValue == null ? null : doAssemble(componentType, sourceValue));
                }
                return dtoArray;
            } else {
                throw new TypeCastException(String.format("Type match Error,target type '%s'", dtoReturnType));
            }
        } else if (dtoReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedReturnType = (ParameterizedType) dtoReturnType;
            Type[] actualTypeArguments = parameterizedReturnType.getActualTypeArguments();
            if (Collection.class.isAssignableFrom((Class) parameterizedReturnType.getRawType())) {
                Collection<?> sourceReturnValues = (Collection<?>) sourceReturnValue;
                Collection<Object> dtoReturnValues = CollectionUtils.emptyCollection(parameterizedReturnType);
                for (Object returnValue : sourceReturnValues) {
                    Object o = doAssemble(actualTypeArguments[0], returnValue);
                    dtoReturnValues.add(o);
                }
                return dtoReturnValues;
            }
            return null;
        } else {
            throw new TypeCastException(String.format("Type match Error,target type '%s'", dtoReturnType));
        }
    }
}
