package com.itsherman.commondto2.core;

import com.itsherman.commondto2.annotations.DtoModel;
import com.itsherman.commondto2.validator.DtoValidator;
import com.itsherman.commondto2.validator.ValidatorHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-27
 */
public class DtoDefinitionHolder {

    private static final Logger log = LoggerFactory.getLogger(DtoDefinitionHolder.class);

    public static Map<Class, DtoDefinition> dtoDefinitions = new HashMap<>();

    public DtoDefinitionHolder() {
    }

    public DtoDefinitionHolder(Collection<Class> classes) {
        if (!classes.isEmpty()) {
            int dtoId = 1;
            for (Class clazz : classes) {
                DtoDefinition dd = new DtoDefinition(clazz);
                dd.setId(dtoId++);
                DtoModel dtoModel = (DtoModel) clazz.getAnnotation(DtoModel.class);
                Class[] fromClass = dtoModel.from();
                dd.setFromClass(fromClass);
                List<DtoValidator> dtoValidators = ValidatorHolder.dtoValidators;
                Set<ValidMessage> validMessages = new HashSet<>();
                for (DtoValidator dtoValidator : dtoValidators) {
                    Set<ValidMessage> validateMsg = dtoValidator.validate(dd, clazz, fromClass);
                    validMessages.addAll(validateMsg);
                }
                if (validMessages.size() > 0) {
                    for (ValidMessage validMessage : validMessages) {
                        log.error(validMessage.getMessage());
                    }
                }
                dtoDefinitions.put(clazz, dd);
            }
        }
    }

    public static Map<Class, DtoDefinition> getDtoDefinitions() {
        return dtoDefinitions;
    }
}
