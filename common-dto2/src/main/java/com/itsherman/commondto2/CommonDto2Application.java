package com.itsherman.commondto2;

import com.itsherman.commondto2.annotations.DtoMapping;
import com.itsherman.commondto2.annotations.EnableDtoMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDtoMapping(mappings = {@DtoMapping(basePackage = "com.itsherman.commondto2.example.dto")})
@SpringBootApplication
public class CommonDto2Application {

    public static void main(String[] args) {
        SpringApplication.run(CommonDto2Application.class, args);
    }

}
