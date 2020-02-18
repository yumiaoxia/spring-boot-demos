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

    private Integer id;

    private Set<Integer> childIds = new HashSet<>();

    private Class<?> dtoClass;

    private Set<DtoPropertyDefinition> validPropertyDefinition = new HashSet<>();

    private Class<?>[] fromClass;

    public DtoDefinition(Class clazz) {
        this.dtoClass = clazz;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Integer> getChildIds() {
        return childIds;
    }

    public void setChildIds(Set<Integer> childIds) {
        this.childIds = childIds;
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
