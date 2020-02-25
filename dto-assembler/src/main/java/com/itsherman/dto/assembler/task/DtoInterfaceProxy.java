package com.itsherman.dto.assembler.task;

import com.itsherman.dto.assembler.constants.Commonconstants;
import com.itsherman.dto.assembler.core.DtoDefinition;
import com.itsherman.dto.assembler.core.DtoPropertyDefinition;
import com.itsherman.dto.assembler.core.InterfaceDtoPropertyDefinition;
import com.itsherman.dto.assembler.exception.DtoAssembleException;
import com.itsherman.dto.assembler.utils.DtoPropertyAssembleUtils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class DtoInterfaceProxy<T, R> implements InvocationHandler {


    private DtoDefinition dtoDefinition;

    private T[] sources;

    private R target;

    public Object getInstance(R target, DtoDefinition dtoDefinition, T... sources) {
        Class<?> dtoClass = dtoDefinition.getDtoClass();
        Class<?>[] interfaces = dtoClass.getInterfaces();
        Class<?>[] finalInterfaces = new Class[interfaces.length + 1];
        finalInterfaces[0] = dtoClass;
        for (int i = 0; i < interfaces.length; i++) {
            finalInterfaces[i + 1] = interfaces[i];
        }
        this.dtoDefinition = dtoDefinition;
        this.sources = sources;
        this.target = target;
        return Proxy.newProxyInstance(dtoDefinition.getDtoClass().getClassLoader(), finalInterfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
                        Optional<?> sourceOptional = Arrays.stream(sources).filter(t -> t.getClass().isAssignableFrom(propertyDefinition.getSourceClass())).findFirst();
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
                                result = DtoPropertyAssembleUtils.doAssemble(returnType, readMethodValue);
                            } catch (RuntimeException e) {
                                throw new DtoAssembleException(String.format("无法编集 %s 到 %s。 目标类型：%s, 原因：%s", readMethod.getName(), propertyDefinition.getDtoMethod().getName(), dtoDefinition.getDtoClass().getSimpleName(), e.getMessage()), e);
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
    }
}
