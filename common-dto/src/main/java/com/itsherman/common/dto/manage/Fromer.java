package com.itsherman.common.dto.manage;

import com.itsherman.common.dto.annotations.DtoMapping;
import com.itsherman.common.dto.exception.DtoAssembleFromException;
import com.itsherman.common.dto.message.ValidMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-10
 */
public class Fromer {
    private static final Logger log = LoggerFactory.getLogger(Fromer.class);

    private Object sources;

    private Selector selector;

    public Fromer(Selector selector) {
        this.selector = selector;
    }

    public TransFormer from(Object... sources) {
        this.sources = sources;
        ValidMessage msg = valid();
        if (!msg.isFlag()) {
            throw new DtoAssembleFromException(msg.getMessage());
        } else if (!"SUCCESS".equals(msg.getMessage())) {
            log.error(msg.getMessage());
        }
        return new TransFormer(this);
    }

    public TransFormer fromList(List<Object> list) {
        this.sources = list;
        return new TransFormer(this);
    }


    private ValidMessage valid() {
        ValidMessage msg = new ValidMessage(true, "SUCCESS");
        Class<?> sourceClazz = sources.getClass();
        boolean isArray = sourceClazz.isArray();
        boolean isCollection = Collection.class.isInstance(sources);

        if (isArray && (sources == null || Array.getLength(sources) == 0)) {
            msg = new ValidMessage(false, "source object must not be blank,can not transform!");
        }
        if (isCollection && CollectionUtils.isEmpty((Collection<?>) sources)) {
            msg = new ValidMessage(false, "source object must not be blank,can not transform!");

        }
        if (selector == null) {
            msg = new ValidMessage(false, "seletor must not be null,missing target to transform");
        }
        Class clazz = selector.getClazz();

        DtoMapping dtoMapping = (DtoMapping) clazz.getAnnotation(DtoMapping.class);
        Class[] froms = dtoMapping.from();
        for (Class from : froms) {
            boolean flag = false;
            if (isArray) {
                Object[] array = (Object[]) sources;
                for (Object srcObject : array) {
                    if (from.isInstance(srcObject)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    msg = new ValidMessage(true, "missing an source: " + from.getName());
                }
            } else if (isCollection) {

            }

        }
        return msg;
    }


    public Object getSources() {
        return sources;
    }

    public void setSources(Object sources) {
        this.sources = sources;
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }
}
