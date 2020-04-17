package com.itsherman.dto.assembler.handler;

import com.itsherman.dto.assembler.annotations.DtoView;
import com.itsherman.dto.assembler.core.InterfacePropertyDefinition;
import com.itsherman.dto.assembler.core.ModelDefinition;
import com.itsherman.dto.assembler.core.ModelPropertyDefinition;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DtoViewRegisHandler {

    public void handle(ModelDefinition md) {
        Class<?> dtoClass = md.getDtoClass();
        Map<Class<?>, List<ModelPropertyDefinition>> map = new HashMap<>();
        List<ModelPropertyDefinition> mpds = md.getModelProperties();
        if (dtoClass.isInterface()) {
            for (ModelPropertyDefinition mpd : mpds) {
                InterfacePropertyDefinition ipd = (InterfacePropertyDefinition) mpd;
                Method dtoMethod = ipd.getDtoMethod();
                DtoView dtoView = dtoMethod.getAnnotation(DtoView.class);
                if (dtoView != null) {
                    Class<?> viewClass = dtoView.viewClass();
                    if (map.containsKey(viewClass)) {
                        List<ModelPropertyDefinition> values = map.get(viewClass);
                        values.add(ipd);
                    } else {
                        map.put(viewClass, Collections.singletonList(ipd));
                    }
                }
            }
        } else {
            for (ModelPropertyDefinition mpd : mpds) {

            }
        }
    }
}
