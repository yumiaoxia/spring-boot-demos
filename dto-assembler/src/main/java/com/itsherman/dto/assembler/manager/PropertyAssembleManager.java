package com.itsherman.dto.assembler.manager;

import com.itsherman.dto.assembler.exception.DtoAssembleException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PropertyAssembleManager {

    private List<PropertyAssembleTask> propertyAssembleTasks = new ArrayList<>();

    public <T, R> R doAssemble(Type targetType, T sourceValue) {
        for (PropertyAssembleTask<T, R> propertyAssembleTask : this.propertyAssembleTasks) {
            if (propertyAssembleTask.check(targetType)) {
                return (R) propertyAssembleTask.assemble(sourceValue);
            }
        }
        throw new DtoAssembleException(String.format("No propertyAssembleTask found,targetType: %s, sourceValue: %s", targetType, sourceValue));
    }


    public PropertyAssembleManager addTask(PropertyAssembleTask propertyAssembleTask) {
        propertyAssembleTasks.add(propertyAssembleTask);
        return this;
    }
}
