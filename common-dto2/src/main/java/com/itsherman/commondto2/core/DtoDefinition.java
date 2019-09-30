package com.itsherman.commondto2.core;

import java.util.HashSet;
import java.util.Set;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
public class DtoDefinition {

    private Class<?> dtoClass;

    private Set<DtoPropertyDefinition> validPropertyDefinition = new HashSet<>();

    private Class<?>[] fromClass;

    public DtoDefinition(Class clazz) {
        this.dtoClass = clazz;
    }

    public Class<?> getDtoClass() {
        return dtoClass;
    }

    public void setDtoClass(Class<?> dtoClass) {
        this.dtoClass = dtoClass;
    }

    public Class<?>[] getFromClass() {
        return fromClass;
    }

    public void setFromClass(Class<?>[] fromClass) {
        this.fromClass = fromClass;
    }

    public Set<DtoPropertyDefinition> getValidPropertyDefinition() {
        return validPropertyDefinition;
    }

    public void setValidPropertyDefinition(Set<DtoPropertyDefinition> validPropertyDefinition) {
        this.validPropertyDefinition = validPropertyDefinition;
    }
}
