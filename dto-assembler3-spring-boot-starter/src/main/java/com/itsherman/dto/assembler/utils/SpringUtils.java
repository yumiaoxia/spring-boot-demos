package com.itsherman.dto.assembler.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz) {
        if (clazz != null) {
            return applicationContext.getBean(clazz);
        }
        return null;
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (clazz != null) {
            return applicationContext.getBean(beanName, clazz);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
    }
}
