package com.itsherman.dto.assembler.core;

public class ModelPropertyDefinition {

    private Class<?> sourceClass;

    private String value;

    private ModelDefinition md;

    public ModelDefinition getMd() {
        return md;
    }

    public void setMd(ModelDefinition md) {
        this.md = md;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }


}
