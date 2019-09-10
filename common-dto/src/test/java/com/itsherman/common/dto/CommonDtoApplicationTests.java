package com.itsherman.common.dto;

import com.itsherman.common.dto.example.Example;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonDtoApplicationTests {

    @Test
    public void contextLoads() {
        Example example = new Example();
        example.doTransform();
    }

}
