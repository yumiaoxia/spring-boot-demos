package com.itsherman.dto.assembler.core;

import java.util.List;

public class ModelDefinition {

    private int id;

    private Class<?> dtoClass;

    private List<Class<?>> fromClasses;

    private List<ModelPropertyDefinition> modelProperties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class<?> getDtoClass() {
        return dtoClass;
    }

    public void setDtoClass(Class<?> dtoClass) {
        this.dtoClass = dtoClass;
    }

    public List<Class<?>> getFromClasses() {
        return fromClasses;
    }

    public void setFromClasses(List<Class<?>> fromClasses) {
        this.fromClasses = fromClasses;
    }

    public List<ModelPropertyDefinition> getModelProperties() {
        return modelProperties;
    }

    public void setModelProperties(List<ModelPropertyDefinition> modelProperties) {
        this.modelProperties = modelProperties;
    }
}
