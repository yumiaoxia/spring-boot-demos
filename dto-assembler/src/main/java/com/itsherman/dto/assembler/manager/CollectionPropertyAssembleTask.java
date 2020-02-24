package com.itsherman.dto.assembler.manager;

import com.itsherman.dto.assembler.utils.CollectionUtils;
import com.itsherman.dto.assembler.utils.DtoAssembleUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

public class CollectionPropertyAssembleTask<T, R> implements PropertyAssembleTask<T, Collection<R>> {

    private ParameterizedType parameterizedType;

    @Override
    public boolean check(Type targetType) {
        if (targetType instanceof ParameterizedType) {
            this.parameterizedType = (ParameterizedType) targetType;
            return Collection.class.isAssignableFrom((Class) parameterizedType.getRawType());
        }
        return false;
    }

    @Override
    public Collection<R> assemble(T sourceValue) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Collection<?> sourceValues = (Collection<?>) sourceValue;
        Collection<R> collection = CollectionUtils.emptyCollection(parameterizedType);
        for (Object value : sourceValues) {
            for (Type actualTypeArgument : actualTypeArguments) {
                collection.add(DtoAssembleUtils.assemble((Class) actualTypeArgument, value));
            }
        }
        return collection;
    }
}
