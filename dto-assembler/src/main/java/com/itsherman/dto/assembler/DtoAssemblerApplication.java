package com.itsherman.dto.assembler;

import com.itsherman.dto.assembler.annotations.DtoMapping;
import com.itsherman.dto.assembler.annotations.EnableDtoMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDtoMapping(mappings = {@DtoMapping(basePackage = "com.itsherman.dto.assembler.example.web.dto")})
@EnableSwagger2
@SpringBootApplication
public class DtoAssemblerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DtoAssemblerApplication.class, args);
    }

}
