package com.itsherman.dto.assembler.core;

import java.util.HashSet;
import java.util.Set;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
public class DtoDefinition {

    private int id;

    private Class<?> dtoClass;

    private Class<?> viewClass;

    private Class<?>[] fromClass;

    private Set<DtoPropertyDefinition> validPropertyDefinitions = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class<?> getViewClass() {
        return viewClass;
    }

    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
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

    public Set<DtoPropertyDefinition> getValidPropertyDefinitions() {
        return validPropertyDefinitions;
    }

    public void setValidPropertyDefinition(Set<DtoPropertyDefinition> validPropertyDefinitions) {
        this.validPropertyDefinitions = validPropertyDefinitions;
    }
}
