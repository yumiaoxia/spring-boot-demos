package com.itsherman.common.dto.manage;

import com.itsherman.common.dto.annotations.DtoMapping;
import com.itsherman.common.dto.annotations.DtoProperty;
import com.itsherman.common.dto.exception.AnnotationException;
import com.itsherman.common.dto.message.ValidMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-10
 */
public class Selector {
    private static final Logger log = LoggerFactory.getLogger(Selector.class);
    private Class clazz;

    public Selector() {
    }

    public Fromer select(Class clazz) {
        this.clazz = clazz;
        if (log.isDebugEnabled()) {
            log.info("开始编集目标类型{}", clazz.getName());
        }
        ValidMessage msg = transformable();
        if (!msg.isFlag()) {
            throw new AnnotationException(msg.getMessage());
        }
        return new Fromer(this);
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    private ValidMessage transformable() {
        ValidMessage msg = new ValidMessage(true, "SUCCESS");
        if (clazz != null) {
            DtoMapping dtoMapping = (DtoMapping) this.clazz.getAnnotation(DtoMapping.class);
            if (dtoMapping != null) {
                Method[] methods = clazz.getMethods();
                Field[] fields = clazz.getDeclaredFields();
                Class[] from = dtoMapping.from();
                for (Method method : methods) {
                    String name = method.getName();
                    if (name.startsWith("get")) {
                        DtoProperty dtoProperty = method.getAnnotation(DtoProperty.class);
                        msg = valid(dtoProperty, from, method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4));
                    }
                }
                for (Field field : fields) {
                    if (!Modifier.isFinal(field.getModifiers())) {
                        DtoProperty dtoProperty = field.getAnnotation(DtoProperty.class);
                        msg = valid(dtoProperty, from, field.getName());
                    }
                }
            } else {
                msg = new ValidMessage(false, "the annotation[DtoMapping] is missing");
            }
        } else {
            msg = new ValidMessage(false, "DTO class must not be null,missing target to transform");
        }
        return msg;
    }

    private ValidMessage valid(DtoProperty dtoProperty, Class[] fromClasses, String property) {
        ValidMessage msg = new ValidMessage(false, "property is not match,element target is " + property);
        if (dtoProperty != null && !dtoProperty.sourceClass().equals(Void.class)) {
            for (Class aClass : fromClasses) {
                if (aClass.equals(dtoProperty.sourceClass())) {
                    msg.setFlag(true);
                    msg.setMessage("SUCCESS");
                    break;
                }
            }
        } else {
            msg.setFlag(true);
            msg.setMessage("SUCCESS");
        }
        return msg;
    }
}
