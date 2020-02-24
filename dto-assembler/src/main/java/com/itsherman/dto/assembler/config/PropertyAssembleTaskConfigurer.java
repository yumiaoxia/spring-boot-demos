package com.itsherman.dto.assembler.config;

import com.itsherman.dto.assembler.manager.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyAssembleTaskConfigurer {

    @Bean
    public SimplePropertyAssembleTask simplePropertyAssembleTask() {
        return new SimplePropertyAssembleTask<>();
    }

    @Bean
    public ArrayPropertyAssembleTask arrayPropertyAssembleTask() {
        return new ArrayPropertyAssembleTask();
    }

    @Bean
    public RawPropertyAssembleTask rawPropertyAssembleTask() {
        return new RawPropertyAssembleTask();
    }

    @Bean
    public CollectionPropertyAssembleTask collectionPropertyAssembleTask() {
        return new CollectionPropertyAssembleTask();
    }

    @Bean
    public PropertyAssembleManager propertyAssembleManager() {
        PropertyAssembleManager propertyAssembleManager = new PropertyAssembleManager();
        propertyAssembleManager
                .addTask(simplePropertyAssembleTask())
                .addTask(rawPropertyAssembleTask())
                .addTask(collectionPropertyAssembleTask())
                .addTask(arrayPropertyAssembleTask());

        return propertyAssembleManager;
    }
}
