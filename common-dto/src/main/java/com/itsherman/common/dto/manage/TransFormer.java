package com.itsherman.common.dto.manage;

import com.itsherman.common.dto.annotations.DtoProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-10
 */
public class TransFormer {
    private static final Logger log = LoggerFactory.getLogger(TransFormer.class);
    private static final String GET = "get";
    private static final String SET = "set";

    private Fromer fromer;

    public TransFormer(Fromer fromer) {
        this.fromer = fromer;
    }

    public Object transform() {
        Selector selector = fromer.getSelector();
        Object[] sources = fromer.getSources();
        Object returnObject = getInstance(selector.getClazz());
        try {
            dtoTransform(sources, returnObject);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return returnObject;
    }

    private Object getInstance(Class clazz) {
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private void dtoTransform(Object[] srcs, Object dest) throws NoSuchMethodException {
        Class destClazz = dest.getClass();
        Map<Method, DtoProperty> methodAnnotationMap = selectWriteMethods(destClazz);
        Map<Method, Method> methodMap = suitMethods(methodAnnotationMap, srcs);
        Set<Map.Entry<Method, Method>> entries = methodMap.entrySet();
        try {
            for (Map.Entry<Method, Method> entry : entries) {
                Method readMethod = entry.getKey();
                for (Object src : srcs) {
                    if (readMethod.getDeclaringClass().equals(src.getClass())) {
                        Object result = entry.getKey().invoke(src, null);
                        if (result != null && !Modifier.isFinal(result.getClass().getModifiers())) {
                            Method writeMethod = entry.getValue();
                            Class<?>[] parameterTypes = writeMethod.getParameterTypes();
                            if (parameterTypes.length == 1) {
                                Class clazz = parameterTypes[0];
                                result = DtoAssembler.builder().select(clazz).from(result).transform();
                            }
                        }
                        entry.getValue().invoke(dest, result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<Method, Method> suitMethods(Map<Method, DtoProperty> methodAnnotationMap, Object... srcObjects) throws NoSuchMethodException {
        Set<Map.Entry<Method, DtoProperty>> entries = methodAnnotationMap.entrySet();
        Map<Method, Method> methodMap = new HashMap<>();
        for (Map.Entry<Method, DtoProperty> entry : entries) {
            Method writeMethod = entry.getKey();
            DtoProperty annotation = entry.getValue();
            Class sourceClazz = annotation.sourceClass();
            String propertyName = annotation.value().equals("") ? writeMethod.getName().substring(3, 4).toLowerCase() + writeMethod.getName().substring(4) : annotation.value();
            String readMethodName = GET + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            for (Object srcObject : srcObjects) {
                Method[] methods = srcObject.getClass().getMethods();
                Optional<Method> methodOptional = Arrays.stream(methods).filter(method -> method.getName().equals(readMethodName)).findFirst();
                if (!methodOptional.isPresent()) {
                    if (Void.class.equals(sourceClazz)) {
                        continue;
                    }
                    if (sourceClazz.isInstance(srcObject)) {
                        NoSuchMethodException ex = new NoSuchMethodException("method is not found,name is " + readMethodName);
                        log.error(ex.getMessage(), ex);
                    }
                } else {
                    Method readMethod = methodOptional.get();
                    int modifiers = readMethod.getModifiers();
                    if (Modifier.isPublic(modifiers)) {
                        methodMap.put(methodOptional.get(), writeMethod);
                    } else {
                        IllegalAccessException ex = new IllegalAccessException("method is not public,name is " + readMethodName);
                        log.error(ex.getMessage(), ex);
                    }
                }
            }
        }
        return methodMap;
    }

    private Map<Method, DtoProperty> selectWriteMethods(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getMethods();
        Map<Method, DtoProperty> methodAnnotationMap = new HashMap<>(fields.length);
        for (Field field : fields) {
            if (field.getType().isPrimitive()) {
                System.out.println(field.getName());
            }
            int modifier = field.getDeclaringClass().getModifiers();
            if (Modifier.isFinal(modifier) || Modifier.isStatic(modifier)) {
                continue;
            }
            DtoProperty annotation = field.getAnnotation(DtoProperty.class);
            if (annotation != null) {
                String fieldName = field.getName();
                String methodName = SET + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                for (Method method : methods) {
                    if (method.getName().equals(methodName)) {
                        if (Modifier.isPublic(method.getModifiers())) {
                            methodAnnotationMap.put(method, annotation);
                            break;
                        }
                    }
                }
            }
        }
        for (Method method : methods) {
            if (method.getName().startsWith(GET)) {
                DtoProperty annotation = method.getAnnotation(DtoProperty.class);
                if (annotation != null) {
                    Optional<Method> methodOptional = Arrays.stream(methods).filter(origin -> origin.getName().equals(method.getName().replaceFirst(GET, SET))).findFirst();
                    if (methodOptional.isPresent()) {
                        Method writeMethod = methodOptional.get();
                        if (Modifier.isPublic(writeMethod.getModifiers()) && methodAnnotationMap.get(writeMethod) == null) {
                            methodAnnotationMap.put(method, annotation);
                        }
                    }
                }
            }
        }
        return methodAnnotationMap;
    }

}
