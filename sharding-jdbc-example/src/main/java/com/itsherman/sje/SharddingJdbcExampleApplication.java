package com.itsherman.sje;

import com.itsherman.commondto2.annotations.DtoMapping;
import com.itsherman.commondto2.annotations.EnableDtoMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableDtoMapping(mappings = {@DtoMapping(basePackage = "com.itsherman.sje.web.dto")})
@SpringBootApplication
public class SharddingJdbcExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharddingJdbcExampleApplication.class, args);
    }

}
