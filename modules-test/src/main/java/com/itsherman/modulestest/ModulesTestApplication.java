package com.itsherman.modulestest;

import com.itsherman.dto.assembler.annotations.DtoMapping;
import com.itsherman.dto.assembler.annotations.EnableDtoMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDtoMapping(mappings = {@DtoMapping(basePackage = "com.itsherman.modulestest.web.dto")})
@SpringBootApplication
public class ModulesTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModulesTestApplication.class, args);
    }

}
