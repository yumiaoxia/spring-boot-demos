package com.itsherman.commondto2;

import com.itsherman.commondto2.example.DtoAccembleExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonDto2ApplicationTests {

    @Test
    public void contextLoads() {
        DtoAccembleExample example = new DtoAccembleExample();
        example.testDtoAssemble();
    }

}
