package com.itsherman.dto.assembler.config;

import com.itsherman.dto.assembler.manager.PropertyAssembleManager;
import com.itsherman.dto.assembler.manager.SimplePropertyAssembleTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyAssembleTaskConfigurer {

    @Bean
    public SimplePropertyAssembleTask simplePropertyAssembleTask() {
        return new SimplePropertyAssembleTask<>();
    }

    @Bean
    public PropertyAssembleManager propertyAssembleManager() {
        PropertyAssembleManager propertyAssembleManager = new PropertyAssembleManager();
        propertyAssembleManager
                .addTask(simplePropertyAssembleTask())
                .addTask()
    }
}
