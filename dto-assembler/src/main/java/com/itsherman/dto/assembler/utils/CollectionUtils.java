package com.itsherman.dto.assembler.utils;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public class CollectionUtils {

    public static Collection<Object> emptyCollection(ParameterizedType type) {
        Collection<Object> collection;
        if (Set.class.isAssignableFrom((Class) type.getRawType())) {
            collection = new HashSet<>();
        } else if (List.class.isAssignableFrom((Class) type.getRawType())) {
            collection = new ArrayList<>();
        } else {
            throw new ClassCastException();
        }
        return collection;
    }

    public static <K, V> Map<K, V> emptyMap(Class<Map<K, V>> clazz) {
        return Collections.emptyMap();
    }

    public boolean isCollection(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz) || Map.class.isAssignableFrom(clazz);
    }
}
